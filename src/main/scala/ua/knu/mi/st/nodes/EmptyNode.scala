package ua.knu.mi.st.nodes

import ua.knu.mi.lexer.Lexeme

class EmptyNode extends Node {
  override def firstLexeme: Lexeme = throw new UnsupportedOperationException

  override def lastLexeme: Lexeme = throw new UnsupportedOperationException

  override def ignore: Boolean = true
}
