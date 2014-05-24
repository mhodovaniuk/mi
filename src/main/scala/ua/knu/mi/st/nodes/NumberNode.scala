package ua.knu.mi.st.nodes

import ua.knu.mi.lexer.Lexeme
import ua.knu.mi.st.nodes.Node._
class NumberNode (number:Number,override val lexeme:Lexeme) extends SimpleNode {
  attributes+=(VALUE->number)

  override def toString: String = lexeme.toString
}