package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.rules._
import ua.knu.mi.utils.RuleUtils

case class RuleARI(name: String, riLists: List[List[ARuleItem]]) extends ARuleItem {
  override def toString: String = name + "::=" + riLists.mkString(" ")

  override def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[RuleRI] = {
    RuleUtils.build(lexemes, ast, this) match {
      case Some(rule) => Some(rule)
      case _ => None
    }
  }
}