package ua.knu.common.utils

import ua.knu.studio.Project
import javafx.scene.control.TreeItem
import ua.knu.mi.st.rules.{RuleRI, RISequence, RuleItem}

object JavaFXUtils {
  def getProjectAsFXTree(project:Project):TreeItem[RuleItem]={
    def processRuleItem(ruleItem:RuleItem):TreeItem[RuleItem]={
      ruleItem match {
        case ri:RISequence => {
          val node=new TreeItem[RuleItem](ri)
          ri.ruleItems.foreach(item=>node.getChildren.add(processRuleItem(item)))
          node
        }
        case ri:RuleRI => {
          val node=new TreeItem[RuleItem](ri)
          ri.rules.foreach(item=>node.getChildren.add(processRuleItem(item)))
          node
        }
        case ri:RuleItem => new TreeItem[RuleItem](ri)
      }
    }
    project.st.root match {
      case Some(root) => processRuleItem(root)
      case None => null
    }
  }
}
