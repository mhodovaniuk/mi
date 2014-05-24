package ua.knu.mi.st.nodes

import ua.knu.mi.lexer.Lexeme
import ua.knu.mi.st.nodes.Node._
class TokenNode (override val lexeme:Lexeme) extends SimpleNode {
  attributes+=(VALUE->lexeme.value)
  override def toString: String = lexeme.toString
}