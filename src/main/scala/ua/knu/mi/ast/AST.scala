package ua.knu.mi.ast

import ua.knu.mi.lexer.{SourceCodeLexemeReader, Lexer}
import scala.collection.mutable
import ua.knu.mi.ast.syntax.RuleARI

class AST(val root: AProgram) extends Serializable {


  val primitiveTypes=List("string", "identifier", "integer")
  val rules=new mutable.HashMap[String,RuleARI]()
  root.syntax match {
    case Some(s)=>{
      for (rule<-s.rules)
        rules+=rule.name->rule
    }
    case None =>
  }
  val types = root.inheritance.build()


  def build(lexemes:SourceCodeLexemeReader) {
    root.build(lexemes,this)
  }

  def findRule(ruleName: String):Option[RuleARI] = {
    rules.get(ruleName)
  }

  def isPrimitiveType(typeName:String):Boolean={
    primitiveTypes.contains(typeName)
  }

  def getRules(ruleName: String): List[RuleARI] = {
    types.typesHierarchy(ruleName).map(t=>rules(t.name)).toList
  }

  override def toString: String = root.toString
}
