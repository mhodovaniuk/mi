package ua.knu.mi.utils

import ua.knu.mi.ast.syntax._
import scala.collection.mutable.ArrayBuffer
import ua.knu.mi.ast._
import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.st.rules._

object RuleUtils {
  def splitRuleOnAltRules(rule: List[ARuleItem]): List[List[ARuleItem]] = {
    val altList = new ArrayBuffer[List[ARuleItem]]()
    var tmpList = new ArrayBuffer[ARuleItem]()
    for (ruleItem <- rule) {
      ruleItem match {
        case item: OrARI => altList += tmpList.toList; tmpList = new ArrayBuffer[ARuleItem]()
        case item => tmpList += item
      }
    }
    altList += tmpList.toList
    altList.toList
  }

  def build(rhLists: List[List[ARuleItem]],lexemes: SourceCodeLexemeReader, ast: AST): Option[List[RuleItem]]={
    for (alternatives <- rhLists) {
      val currOffset = lexemes.offset
      val res = new ArrayBuffer[Option[RuleItem]]()
      var i = 0
      var isExistNone=false
      while (!isExistNone && i < alternatives.size) {
        res += alternatives(i).build(lexemes, ast)
        i += 1
        isExistNone=res.last match {
          case None => true
          case _ => false
        }
      }
      if (!isExistNone)
        return Some(res.map(_.get).toList)
      else
        lexemes.offset = currOffset
    }
    None
  }

  def build(lexemes: SourceCodeLexemeReader, ast: AST, rule: RuleARI): Option[RuleRI] = {
    build(rule.riLists,lexemes,ast) match {
      case Some(ruleItems)=>Some(RuleRI(rule.name,ruleItems))
      case _ => None
    }
  }

  def build(lexemes: SourceCodeLexemeReader, ast: AST, rules: List[RuleARI]): Option[RuleRI] = {
    for (rule<-rules){
      build(lexemes,ast,rule) match {
        case Some(ruleItem) =>return Some(ruleItem)
        case _ =>
      }
    }
    None
  }
}
