package ua.knu.mi.st.nodes
import ua.knu.mi.st.nodes.Node._

class RuleNode(ruleName:String,subNodes:List[Node]) extends ComplexNode {
  attributes+=(RULE_NAME->ruleName)
  attributes+=(SUB_NODES->subNodes.filter(!_.isInstanceOf[EmptyNode]))
  def name:String = attributes(RULE_NAME).asInstanceOf[String]
  override def toString: String = "Rule: "+name
}


