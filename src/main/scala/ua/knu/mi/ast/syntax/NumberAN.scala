package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.{Lexeme, SourceCodeLexemeReader}
import ua.knu.mi.st.nodes._
import ua.knu.mi.ast.AST
import ua.knu.mi.utils.SomeList

case class NumberAN(number: Number) extends ANode {
  override def toString: String = number.toString


  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[Node] = {
    if (lexemes.hasNext && lexemes.tryNextLexeme().value == number.toString)
      Some(new NumberNode(number, lexemes.nextLexeme()))
    else None
  }
}

object NumberClass extends Enumeration {
  type QuantifierType = Value
  val INTEGER, REAL = Value
}
