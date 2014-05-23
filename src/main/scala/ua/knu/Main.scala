package ua.knu

import ua.knu.mi.MIParser
import ua.knu.mi.ast._
import ua.knu.common.ASTSerializationService

object Main {
  def main(args: Array[String]) {
    val ast=MIParser.parseConfigFile("test2.txt")
    //println(ast)
    println(ASTSerializationService.fromByteArray(ASTSerializationService.toByteArray(ast)))
    //val res=p.parseAll(p.PROGRAM,text);
    //if (res.successful) println(res.get) else println("fail")
  }
}