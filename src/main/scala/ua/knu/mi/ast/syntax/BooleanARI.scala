package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.rules.{BooleanRI, RuleItem}

case class BooleanARI(boolVal: Boolean) extends ARuleItem {
  override def toString: String = boolVal.toString

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[RuleItem] = {
    if (lexemes.hasNext && lexemes.tryNextLexeme().value == boolVal.toString)
      Some(BooleanRI(boolVal, lexemes.nextLexeme()))
    else None
  }
}
