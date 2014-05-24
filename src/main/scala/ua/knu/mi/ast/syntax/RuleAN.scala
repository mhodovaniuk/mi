package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.nodes._
import ua.knu.mi.utils.{SomeList, RuleUtils}

case class RuleAN(name: String, riLists: List[List[ANode]]) extends ANode{
  override def toString: String = name + "::=" + riLists.mkString(" ")

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[RuleNode]] = {
    RuleUtils.buildFromRule(lexemes, ast, this) match {
      case Some(rule) => SomeList(rule)
      case _ => None
    }
  }
}