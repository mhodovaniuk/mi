package ua.knu.mi.ast.syntax

import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.ast.AST
import ua.knu.mi.st.rules._

trait ARuleItem {
  def build(lexemes: SourceCodeLexemeReader, ast: AST): Option[RuleItem]
}
