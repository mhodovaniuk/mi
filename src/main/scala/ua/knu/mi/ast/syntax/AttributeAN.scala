package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.utils.{SomeList, RuleUtils}
import ua.knu.mi.st.nodes._

case class AttributeAN(name: String, className: String) extends ComplexANode {
  override def toString: String = name  + "=>" + className

  def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[Node] = {
    if (lexemes.hasNext) {
      if (ast.types.isSubtypeOf(lexemes.tryNextLexeme().className, className)) {
        return Some(new AttributeNode(name, className, lexemes.nextLexeme()))
      } else {
        return RuleUtils.buildFromSubTypes(lexemes, ast, ast.getSubTypes( className)) match {
          case Some(ruleItem) => Some(ruleItem)
          case _ => None
        }
      }
    }
    None
  }
}