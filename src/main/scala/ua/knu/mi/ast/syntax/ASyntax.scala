package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.nodes._
import ua.knu.mi.exceptions.ParseException

case class ASyntax(startRuleName: String, rules: List[RuleAN]) {
  override def toString: String = "SYNTAX.\nStart=" + startRuleName + "\n" + rules.mkString("", ";\n", ";")

  def build(lexemes: SourceCodeLexemeReader, ast: AST): Node = {
    ast.findRule(startRuleName) match {
      case Some(startRule) => {
        startRule.build(lexemes, ast) match {
          case Some(l)=>l
          case None => throw new ParseException("Can't build syntax tree")
        }
      }
      case _ => throw new ParseException("Can't find start rule.")
    }
  }
}