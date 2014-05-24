package ua.knu.mi.st.nodes

import ua.knu.mi.lexer.Lexeme
import ua.knu.mi.st.nodes.Node._
class AttributeNode(grammarItemName:String,grammarClassName:String,override val lexeme:Lexeme) extends SimpleNode{

  attributes+=(GRAMMAR_CLASS_NAME->grammarClassName)
  attributes+=(VALUE->lexeme.value)
  attributes+=(GRAMMAR_ITEM_NAME->grammarItemName)
  override def toString: String = if (grammarClassName==className)
    super.toString
  else lexeme.value+" ["+grammarClassName+", "+lexeme.className+"]"
}