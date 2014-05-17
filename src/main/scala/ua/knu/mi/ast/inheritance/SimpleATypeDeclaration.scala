package ua.knu.mi.ast.inheritance

import ua.knu.mi.st.types._

case class SimpleATypeDeclaration(name: String) extends ATypeDeclaration {
  override def toString: String = name

  override def build(): Type = new Type(name)
}
