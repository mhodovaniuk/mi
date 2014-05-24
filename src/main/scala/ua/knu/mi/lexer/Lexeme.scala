package ua.knu.mi.lexer

class Lexeme(val value:String,val className:String, val caseSensitive:Boolean) {
  var id:Int= 0
  override def toString: String =  "\""+value+"\""+" ["+className+"]"
}
