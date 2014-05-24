package ua.knu.common.utils

import ua.knu.studio.Project
import javafx.scene.control.TreeItem
import ua.knu.mi.st.nodes.{RuleNode, Node}

object JavaFXUtils {
  def getProjectAsFXTree(project:Project):TreeItem[Node]={
    def processRuleItem(ruleItem:Node):TreeItem[Node]={
      ruleItem match {
        case ri:RuleNode => {
          val node=new TreeItem[Node](ri)
          ri.nodes
          ri.nodes.foreach(item=>node.getChildren.add(processRuleItem(item)))
          node
        }
        case ri:Node => new TreeItem[Node](ri)
      }
    }
    project.st.root match {
      case Some(root:Node) => processRuleItem(root)
      case None => null
    }
  }
}
