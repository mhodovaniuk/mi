package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import scala.collection.mutable.ArrayBuffer
import ua.knu.mi.st.nodes._
import ua.knu.mi.utils.RuleUtils
import ua.knu.mi.ast.syntax.QuantifierType.QuantifierType

case class QuantifierAN(quantifier: QuantifierType, rhLists: List[List[ANode]], separator: String) extends ANode {
  override def toString: String = "[" + rhLists.mkString(" ") + "]" + QuantifierType.toString(quantifier) + "\"" + separator + "\""

  private def buildRIList(lexemes: SourceCodeLexemeReader, ast: AST): (Option[List[Node]],Int) = {
    def readSeparator(): Option[Node] = {
      if (lexemes.hasNext && lexemes.tryNextLexeme().value == separator) {
        val lexeme = lexemes.nextLexeme()
        Some(new TokenNode(lexeme))
      } else None
    }
    var count=0
    val res = new ArrayBuffer[Node]()
    RuleUtils.buildFromAlternativesList(rhLists, lexemes, ast) match {
      case Some(arr) => count+=1;res ++= arr
      case None => return (None,0)
    }

    while (true) {
      readSeparator() match {
        case Some(ruleItem) =>
          RuleUtils.buildFromAlternativesList(rhLists, lexemes, ast) match {
            case Some(arr) =>
              count+=1
              res += ruleItem
              res ++= arr
            case None =>
              lexemes.prevLexeme()
              return (Some(res.toList),count)
          }
        case None => return (Some(res.toList),count)
      }
    }
    (Some(res.toList),count)
  }

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[Node] = {
    val startOffset = lexemes.offset
    val (ruleItems, count)=buildRIList(lexemes, ast)
    ruleItems match {
      case Some(ruleItems) =>
        val isCorrect = quantifier match {
          case QuantifierType.ONE_OR_ZERO => count <= 1
          case QuantifierType.AT_LEAST_ONE => count >= 1
          case _ => true
        }
        if (isCorrect)
          Some(new SequenceNode(ruleItems,separator,count))
        else {
          lexemes.offset = startOffset
          None
        }
      case _ => None
    }
  }

}

object QuantifierType extends Enumeration {
  type QuantifierType = Value
  val ONE_OR_ZERO, AT_LEAST_ONE, ANY = Value

  def toString(q: QuantifierType): String = q match {
    case ONE_OR_ZERO => "!"
    case AT_LEAST_ONE => "+"
    case ANY => "*"
  }
}
