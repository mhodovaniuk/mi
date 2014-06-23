package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.nodes._
import ua.knu.mi.exceptions.ParseException
import ua.knu.mi.utils.RuleUtils

case class ASyntax(startRuleName: String, rules: List[RuleAN]) {
  override def toString: String = "SYNTAX.\nStart=" + startRuleName + "\n" + rules.mkString("", ";\n", ";")

  def build(lexemes: SourceCodeLexemeReader, ast: AST): Node = {
    RuleUtils.buildFromSubTypes(lexemes, ast, ast.getSubTypes(startRuleName)) match {
      case Some(rule) => rule
      case None => throw new ParseException("Can't build syntax tree")
    }
  }
}