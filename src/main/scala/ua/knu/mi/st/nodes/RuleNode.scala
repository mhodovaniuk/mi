package ua.knu.mi.st.nodes
import ua.knu.mi.st.nodes.Node._
import ua.knu.mi.lexer.Lexeme

class RuleNode(ruleName:String,subNodes:List[Node]) extends ComplexNode {
  attributes+=(RULE_NAME->ruleName)
  attributes+=(SUB_NODES->subNodes)
  def name:String = attributes(RULE_NAME).asInstanceOf[String]
  override def toString: String = "Rule: "+name
}


