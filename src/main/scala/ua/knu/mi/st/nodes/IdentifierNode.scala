package ua.knu.mi.st.nodes

import ua.knu.mi.lexer.Lexeme
import ua.knu.mi.st.nodes.Node._
case class IdentifierNode (identifier:String, override val lexeme:Lexeme) extends SimpleNode {

}
