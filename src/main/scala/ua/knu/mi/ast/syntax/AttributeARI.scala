package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.utils.RuleUtils
import ua.knu.mi.st.rules._

case class AttributeARI(key: String, value: String) extends ARuleItem {
  override def toString: String = key + " => " + value

  def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[RuleItem] = {
    if (lexemes.hasNext) {
      if (ast.isPrimitiveType(value)) {
        //        if (ast.types.isSubtypeOf(lexemes.tryNextLexeme().className, value))
        if (lexemes.tryNextLexeme().className == value)
          Some(WithClassRI(lexemes.nextLexeme().value, value))
        else None
      } else {
        RuleUtils.build(lexemes, ast, ast.getRules(value)) match {
          case Some(ruleItem) => Some(ruleItem)
          case _ => None
        }
      }
    } else {
      None
    }
  }
}