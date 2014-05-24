package ua.knu.mi.st.nodes

import ua.knu.mi.lexer.Lexeme
import ua.knu.mi.st.nodes.Node._
trait SimpleNode extends Node{
  val lexeme:Lexeme
  attributes+=(LEXEME_CLASS_NAME->className)
  def value=attributes(VALUE)
  def className=lexeme.className
  def textValue=lexeme.value
  override def toString: String = lexeme.toString
}
