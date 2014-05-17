package ua.knu.mi.lexer

class Lexeme(val value:String,val className:String, val caseSensitive:Boolean) {

  override def toString: String =  "\""+value+"\""+"{"+className+"}"
}
