package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.rules._
import ua.knu.mi.exceptions.ParseException

case class ASyntax(startRuleName: String, rules: List[RuleARI]) {
  override def toString: String = "SYNTAX.\nStart=" + startRuleName + "\n" + rules.mkString("", ";\n", ";")

  def build(lexemes: SourceCodeLexemeReader, ast: AST): RuleRI = {
    ast.findRule(startRuleName) match {
      case Some(startRule) => startRule.build(lexemes, ast).get
      case _ => throw new ParseException("Can't find start rule.")
    }
  }
}