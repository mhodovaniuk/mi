package ua.knu.mi.ast

import ua.knu.mi.ast.inheritance.AInheritance
import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.st.Program
import ua.knu.mi.ast.syntax.ASyntax

case class AProgram(inheritance: AInheritance, syntax: Option[ASyntax]) {
  def this(inheritance: AInheritance) {
    this(inheritance, None)
  }

  def this(inheritance: AInheritance, syntax: ASyntax) {
    this(inheritance, Some(syntax))
  }

  override def toString: String = if (syntax == null)
    inheritance.toString
  else inheritance.toString + syntax.toString

  def build(lexemes: SourceCodeLexemeReader, ast: AST) {
    new Program(ast.types, syntax match {
      case Some(s) => Some(s.build(lexemes, ast))
      case _ => None
    })
  }
}