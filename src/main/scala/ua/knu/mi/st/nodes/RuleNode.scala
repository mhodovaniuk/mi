package ua.knu.mi.st.nodes
import ua.knu.mi.st.nodes.Node._
import ua.knu.mi.lexer.Lexeme

class RuleNode(ruleName:String,ruleNodes:List[Node]) extends Node {
  attributes+=(RULE_NAME->ruleName)
  attributes+=(RULE_NODES->ruleNodes)
  def name:String = attributes(RULE_NAME).asInstanceOf[String]
  def nodes:List[Node] = attributes(RULE_NODES).asInstanceOf[List[Node]]
  override def toString: String = "Rule: "+name

  override def firstLexeme: Lexeme = ruleNodes.head.firstLexeme

  override def lastLexeme: Lexeme = ruleNodes.last.lastLexeme
}


