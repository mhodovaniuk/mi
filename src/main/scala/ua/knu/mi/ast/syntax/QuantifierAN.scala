package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import scala.collection.mutable.ArrayBuffer
import ua.knu.mi.st.nodes._
import ua.knu.mi.utils.RuleUtils
import ua.knu.mi.ast.syntax.QuantifierType.QuantifierType

case class QuantifierAN(quantifier: QuantifierType, rhLists: List[List[ANode]], separator: String) extends ANode {
  override def toString: String = "[" + rhLists.mkString(" ") + "]" + QuantifierType.toString(quantifier) + "\"" + separator + "\""

  private def buildRIList(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[Node]] = {
    def readSeparator(): Option[Node] = {
      if (lexemes.hasNext && lexemes.tryNextLexeme().value == separator) {
        val lexeme = lexemes.nextLexeme()
        Some(new TokenNode(lexeme))
      } else None
    }
//TODO:return some case class, not array
    val res = new ArrayBuffer[Node]()
    RuleUtils.buildFromAlternativesList(rhLists, lexemes, ast) match {
      case Some(arr) => res ++= arr
      case None => return None
    }

    while (true) {
      readSeparator() match {
        case Some(ruleItem) =>
          RuleUtils.buildFromAlternativesList(rhLists, lexemes, ast) match {
            case Some(arr) =>
              res += ruleItem
              res ++= arr
            case None =>
              lexemes.prevLexeme()
              return Some(res.toList)
          }
        case None => return Some(res.toList)
      }
    }
    Some(res.toList)
  }

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[Node]] = {
    val startOffset = lexemes.offset
    buildRIList(lexemes, ast) match {
      case Some(ruleItems) =>
        val isCorrect = quantifier match {
          case QuantifierType.ONE_OR_ZERO => ruleItems.size <= 1
          case QuantifierType.AT_LEAST_ONE => ruleItems.size >= 1
          case _ => true
        }
        if (isCorrect)
          Some(ruleItems)
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
