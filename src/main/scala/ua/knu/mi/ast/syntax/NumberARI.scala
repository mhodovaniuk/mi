package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.{Lexeme, SourceCodeLexemeReader}
import ua.knu.mi.st.rules._
import ua.knu.mi.ast.AST

case class NumberARI(number: Number) extends ARuleItem {
  override def toString: String = number.toString


  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[RuleItem] = {
    if (lexemes.hasNext && lexemes.tryNextLexeme().value == number.toString)
      Some(NumberRI(number, lexemes.nextLexeme()))
    else None
  }
}

object NumberClass extends Enumeration {
  type QuantifierType = Value
  val INTEGER, REAL = Value
}
