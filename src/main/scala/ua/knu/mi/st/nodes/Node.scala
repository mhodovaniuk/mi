package ua.knu.mi.st.nodes

import scala.collection.mutable
import ua.knu.mi.lexer.Lexeme

trait Node {
  val attributes=mutable.HashMap[String,Any]()
  def visibleAttributes = attributes.filter(p=>{!Node.IGNORE_ATTRIBUTES.contains(p._1)})
  def lexemesRange:(Lexeme,Lexeme)=(firstLexeme,lastLexeme)
  def firstLexeme:Lexeme
  def lastLexeme:Lexeme
  def ignore = false
}

object Node{
  val VALUE="value"
  val GRAMMAR_CLASS_NAME="grammar class"
  val RULE_NAME="rule name"
  val SUB_NODES="sub nodes"
  val LEXEME_CLASS_NAME="lexeme class"
  val GRAMMAR_ITEM_NAME="grammar item"
  val COUNT="count"
  val SEPARATOR="separator"
  val IGNORE_ATTRIBUTES=Seq(SUB_NODES)

}