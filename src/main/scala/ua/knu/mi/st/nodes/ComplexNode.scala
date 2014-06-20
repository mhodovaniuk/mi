package ua.knu.mi.st.nodes

import ua.knu.mi.st.nodes.Node._
import ua.knu.mi.lexer.Lexeme

trait ComplexNode extends Node {
  def nodes:List[Node] = attributes(SUB_NODES).asInstanceOf[List[Node]]


  override def firstLexeme: Lexeme = nodes.head.firstLexeme
  override def lastLexeme: Lexeme = nodes.last.lastLexeme
}
