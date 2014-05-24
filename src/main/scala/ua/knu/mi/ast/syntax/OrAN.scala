package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.nodes._

class OrAN extends ANode {

  override def toString: String = " | "

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[Node]] = {
    throw new UnsupportedOperationException()
  }
}
