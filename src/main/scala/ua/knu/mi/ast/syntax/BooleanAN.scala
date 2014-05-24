package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.nodes.{BooleanNode, Node}
import ua.knu.mi.utils.SomeList

case class BooleanAN(boolVal: Boolean) extends ANode {
  override def toString: String = boolVal.toString

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[Node]] = {
    if (lexemes.hasNext && lexemes.tryNextLexeme().value == boolVal.toString)
      SomeList(new BooleanNode(lexemes.nextLexeme()))
    else None
  }
}
