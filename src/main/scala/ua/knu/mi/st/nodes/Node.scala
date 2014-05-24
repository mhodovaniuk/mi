package ua.knu.mi.st.nodes

import scala.collection.mutable

trait Node {
  val attributes=mutable.HashMap[String,Any]()
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