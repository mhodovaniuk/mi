package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.nodes._
import ua.knu.mi.utils.SomeList

case class TokenAN(token: String) extends ANode {
  override def toString: String = "\"" + token + "\""

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[Node]] = {
    if (lexemes.hasNext && lexemes.tryNextLexeme().value == token) {
      val lexeme = lexemes.nextLexeme()
      SomeList(new TokenNode(lexeme))
    } else None
  }
}