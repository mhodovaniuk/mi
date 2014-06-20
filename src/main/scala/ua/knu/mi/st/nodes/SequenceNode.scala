package ua.knu.mi.st.nodes

import ua.knu.mi.st.nodes.Node._

class SequenceNode(val subNodes:List[Node],val separator:String,val count:Int) extends ComplexNode {
  attributes+=(COUNT->count)
  attributes+=(SEPARATOR->separator)
  attributes+=(SUB_NODES->subNodes)
  

  override def toString: String = "SEQUENCE"
}
