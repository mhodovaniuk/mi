package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.rules._

case class TokenARI(token: String) extends ARuleItem {
  override def toString: String = "\"" + token + "\""

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[RuleItem] = {
    if (lexemes.hasNext && lexemes.tryNextLexeme().value == token) {
      val lexeme = lexemes.nextLexeme()
      Some(TokenRI(lexeme.value, lexeme))
    } else None
  }
}