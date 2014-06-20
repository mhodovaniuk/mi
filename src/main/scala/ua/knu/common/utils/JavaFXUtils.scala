package ua.knu.common.utils

import ua.knu.studio.Project
import javafx.scene.control.TreeItem
import javafx.scene.text.Text
import ua.knu.mi.st.nodes.{ComplexNode, RuleNode, Node}
import ua.knu.gui.components.LexemeText
import java.util
import scala.util

object JavaFXUtils {
  def getProjectAsFXTree(project:Project):TreeItem[Node]={
    def processRuleItem(ruleItem:Node):TreeItem[Node]={
      ruleItem match {
        case ri:ComplexNode => {
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

  def getLexemesAsFXText(project:Project):java.util.List[LexemeText] = {
    val res=new java.util.ArrayList[LexemeText]()
    for (lexeme<-project.sclr.allLexemes){

        res.add(new LexemeText(lexeme))
    }
    res
  }
}
