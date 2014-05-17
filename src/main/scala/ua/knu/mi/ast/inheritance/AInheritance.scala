package ua.knu.mi.ast.inheritance

import ua.knu.mi.st.types._

case class AInheritance(types: List[ATypeDeclaration]) {
  override def toString: String = "INHERITANCE.\n" + types.mkString("", ".\n", ".\n")

  def build(): Types = new Types(for (t <- types) yield t.build())
}
