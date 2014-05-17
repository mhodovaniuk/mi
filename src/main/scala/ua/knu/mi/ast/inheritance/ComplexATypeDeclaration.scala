package ua.knu.mi.ast.inheritance

import ua.knu.mi.st.types._

case class ComplexATypeDeclaration(name: String, declaration: List[ATypeDeclaration]) extends ATypeDeclaration {
  override def toString: String = name + ":" + "<" + declaration.mkString(";\n") + ">"

  override def build(): Type = Type(name, Some(for (t <- declaration) yield t.build()))
}