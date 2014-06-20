package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.nodes._

case class IdentifierAN (identifier: String) extends ANode {
  override def toString: String = identifier

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[Node] = {
    throw new UnsupportedOperationException()
    //    if (lexemes.hasNext && lexemes.tryNextLexeme().value==identifier)
    //      Some(IdentifierRI(lexemes.nextLexeme().value))
    //    else None
  }
}
