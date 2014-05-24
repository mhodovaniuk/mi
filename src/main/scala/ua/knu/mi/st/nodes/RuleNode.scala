package ua.knu.mi.st.nodes
import ua.knu.mi.st.nodes.Node._
class RuleNode(ruleName:String,ruleNodes:List[Node]) extends Node {
  attributes+=(RULE_NAME->ruleName)
  attributes+=(RULE_NODES->ruleNodes)
  def name:String = attributes(RULE_NAME).asInstanceOf
  def nodes:List[Node] = attributes(RULE_NODES).asInstanceOf
  override def toString: String = "Rule: "+name
}


