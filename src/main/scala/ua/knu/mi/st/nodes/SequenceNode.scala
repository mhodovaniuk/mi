package ua.knu.mi.st.nodes

import ua.knu.mi.st.nodes.Node._

class SequenceNode(val subNodes:List[Node],val separator:Option[String],val count:Int) extends ComplexNode {
  attributes+=(COUNT->count)
  separator match {
    case Some(s)=>attributes+=(SEPARATOR->s)
    case None =>
  }
  attributes+=(SUB_NODES->subNodes.filter(!_.isInstanceOf[EmptyNode]))
  

  override def toString: String = "SEQUENCE"
}
