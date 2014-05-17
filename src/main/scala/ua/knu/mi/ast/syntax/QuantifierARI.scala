package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import scala.collection.mutable.ArrayBuffer
import ua.knu.mi.st.rules._
import ua.knu.mi.utils.RuleUtils
import ua.knu.mi.ast.syntax.QuantifierType.QuantifierType

case class QuantifierARI(quantifier: QuantifierType, rhLists: List[List[ARuleItem]], separator: String) extends ARuleItem {
  override def toString: String = "[" + rhLists.mkString(" ") + "]" + QuantifierType.toString(quantifier) + "\"" + separator + "\""

  private def buildRIList(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[RuleItem]] = {
    def readSeparator(): Option[RuleItem] = {
      if (lexemes.hasNext && lexemes.tryNextLexeme().value == separator) {
        val lexeme = lexemes.nextLexeme()
        Some(TokenRI(lexeme.value, lexeme))
      } else None
    }

    val res = new ArrayBuffer[RuleItem]()
    RuleUtils.build(rhLists, lexemes, ast) match {
      case Some(arr) => res ++= arr
      case None => return None
    }

    while (true) {
      readSeparator() match {
        case Some(ruleItem) =>
          RuleUtils.build(rhLists, lexemes, ast) match {
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

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[RuleItem] = {
    val startOffset = lexemes.offset
    buildRIList(lexemes, ast) match {
      case Some(ruleItems) =>
        var isCorrect = false
        quantifier match {
          case QuantifierType.ONE_OR_ZERO => isCorrect = if (ruleItems.size <= 1) true else false
          case QuantifierType.AT_LEAST_ONE => isCorrect = if (ruleItems.size >= 1) true else false
          case _ => isCorrect = true
        }
        if (isCorrect)
          Some(RISequence(ruleItems))
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
