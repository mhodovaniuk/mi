package ua.knu

import ua.knu.mi.MIParser
import ua.knu.mi.ast._
import ua.knu.mi.utils.ASTSerializationService

object Main {
  def main(args: Array[String]) {
    val p=new MIParser
    val ast=p.parseFile("test2.txt")
    //println(ast)
    println(ASTSerializationService.fromByteArray(ASTSerializationService.toByteArray(ast)))
    //val res=p.parseAll(p.PROGRAM,text);
    //if (res.successful) println(res.get) else println("fail")
  }
}