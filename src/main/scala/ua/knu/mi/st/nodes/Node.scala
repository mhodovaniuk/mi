package ua.knu.mi.st.nodes

import scala.collection.mutable
import ua.knu.mi.lexer.Lexeme

trait Node {
  val attributes=mutable.HashMap[String,Any]()
  def visibleAttributes = attributes.filter(p=>{!Node.IGNORE_ATTRIBUTES.contains(p._1)})
  def lexemesRange:(Lexeme,Lexeme)=(firstLexeme,lastLexeme)
  def firstLexeme:Lexeme
  def lastLexeme:Lexeme
}

object Node{
  val VALUE="value"
  val GRAMMAR_CLASS_NAME="grammar class"
  val RULE_NAME="rule name"
  val RULE_NODES="rule nodes"
  val LEXEME_CLASS_NAME="lexeme class"
  val GRAMMAR_ITEM_NAME="grammar item"
  val IGNORE_ATTRIBUTES=Seq(RULE_NODES)
}