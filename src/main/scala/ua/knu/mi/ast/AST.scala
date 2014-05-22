package ua.knu.mi.ast

import ua.knu.mi.lexer.{SourceCodeLexemeReader, Lexer}
import scala.collection.mutable
import ua.knu.mi.ast.syntax.RuleARI
import ua.knu.mi.st.ST

class AST(val root: AProgram) extends Serializable {
  var st:ST=null
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


  def build(lexemes:SourceCodeLexemeReader):ST= {
    st=root.build(lexemes,this)
    st
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
