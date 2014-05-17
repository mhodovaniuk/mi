package ua.knu.mi

import ua.knu.mi.lexer.{SourceCodeLexemeReader, Lexer}

object Tests {
  def main(args: Array[String]) {
    val lexer=new Lexer("lexer.json")
    println(lexer.parse(io.Source.fromFile("sql-example.txt").getLines().mkString("\n")))
//    val sclr=new SourceCodeLexemeReader()
  }
}
