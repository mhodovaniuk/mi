package ua.knu.mi.utils

import ua.knu.mi.st.nodes.Node

object SomeList {
  def apply[N <: Node](x : N):Some[List[N]]={
    Some(List(x))
  }
}
