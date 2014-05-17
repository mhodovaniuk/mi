package ua.knu.mi.ast.inheritance

import ua.knu.mi.st.types.Type

trait ATypeDeclaration {
  def build(): Type
}
