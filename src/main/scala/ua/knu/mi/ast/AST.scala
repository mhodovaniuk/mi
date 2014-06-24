package ua.knu.mi.ast

import ua.knu.mi.lexer.{SourceCodeLexemeReader, Lexer}
import scala.collection.mutable
import ua.knu.mi.ast.syntax.{AttributeAN, ComplexANode, ANode, RuleAN}
import ua.knu.mi.st.ST

class AST(val root: AProgram) extends Serializable {
  var st: ST = null
  val primitiveTypes = List("string", "identifier", "integer")
  val rules = new mutable.HashMap[String, RuleAN]()
  root.syntax match {
    case Some(s) => {
      for (rule <- s.rules)
        rules += rule.name -> rule
    }
    case None =>
  }
  val types = root.inheritance.build()


  def build(lexemes: SourceCodeLexemeReader): ST = {
    st = root.build(lexemes, this)
    st
  }

  def findRule(ruleName: String): Option[RuleAN] = {
    rules.get(ruleName)
  }

  def isPrimitiveType(typeName: String): Boolean = {
    primitiveTypes.contains(typeName)
  }

  def getSubTypes(ruleName: String): List[ComplexANode] = {
    types.typesHierarchy.get(ruleName) match {
      case Some(l) => l.filter(p => rules.contains(p) || primitiveTypes.contains(p)).
        map(typeName => rules.get(typeName) match {
        case Some(rule) => rule
        case None => AttributeAN(ruleName, typeName)
      }).toList
      case None => rules.get(ruleName) match {
        case Some(rule) => List(rule)
        case None => List()
      }
    }
  }

  override def toString: String = root.toString
}
