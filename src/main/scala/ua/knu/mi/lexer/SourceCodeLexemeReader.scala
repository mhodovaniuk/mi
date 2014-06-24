package ua.knu.mi.lexer

import scala.collection.mutable.ArrayBuffer

class SourceCodeLexemeReader(val sourceCode: String, val lexer: Lexer) {

  val allLexemes: ArrayBuffer[Lexeme] = lexer.parse(sourceCode)
  val lexemes = allLexemes.filter(l => !lexer.ignoreClasses.contains(l.className))
  var offset = -1

  def hasNext: Boolean = offset < lexemes.length-1

  private def checkOffset(offset: Int) {
    if (offset >= lexemes.length || offset < 0)
      throw new IndexOutOfBoundsException()
  }

  def nextLexeme() = {
    checkOffset(offset + 1)

    offset += 1
    lexemes(offset)
  }

  def currentLexeme(): Lexeme = {
    lexemes(offset)
  }

  def prevLexeme() = {
    checkOffset(offset - 1)
    offset -= 1
    lexemes(offset)
  }

  def tryNextLexeme() = {
    checkOffset(offset + 1)
    lexemes(offset + 1)
  }

  def tryPrev() = {
    checkOffset(offset - 1)
    lexemes(offset - 1)
  }

  def moveOffset(delta: Int) {
    checkOffset(offset + delta)
    offset += delta
  }
}

object SourceCodeLexemeReader {
  def createSourceCodeLexemeReaderFromSourceCodeFile(fileName: String, lexer: Lexer): SourceCodeLexemeReader = {
    new SourceCodeLexemeReader(io.Source.fromFile(fileName).getLines().mkString("\n"), lexer)
  }

  def createSourceCodeLexemeReaderFromSourceCode(json: String, lexer: Lexer): SourceCodeLexemeReader = {
    new SourceCodeLexemeReader(json, lexer)
  }
}