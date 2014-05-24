package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.utils.{SomeList, RuleUtils}
import ua.knu.mi.st.nodes._

case class AttributeAN(key: String, value: String) extends ANode {
  override def toString: String = key + " => " + value

  def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[List[Node]] = {
    if (lexemes.hasNext) {
      if (ast.types.isSubtypeOf(lexemes.tryNextLexeme().className, value)) {
        SomeList(new AttributeNode(key, value, lexemes.nextLexeme()))
      } else {
        RuleUtils.buildFromComplex(lexemes, ast, ast.getRules(key, value)) match {
          case Some(ruleItem) => Some(List(ruleItem))
          case _ => None
        }
      }
    }
    None
  }
}