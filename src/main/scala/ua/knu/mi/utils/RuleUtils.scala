package ua.knu.mi.utils

import ua.knu.mi.ast.syntax._
import scala.collection.mutable.ArrayBuffer
import ua.knu.mi.ast._
import ua.knu.mi.lexer.SourceCodeLexemeReader
import ua.knu.mi.st.nodes._

object RuleUtils {
  def splitRuleOnAltRules(rule: List[ANode]): List[List[ANode]] = {
    val altList = new ArrayBuffer[List[ANode]]()
    var tmpList = new ArrayBuffer[ANode]()
    for (ruleItem <- rule) {
      ruleItem match {
        case item: OrAN => altList += tmpList.toList; tmpList = new ArrayBuffer[ANode]()
        case item => tmpList += item
      }
    }
    altList += tmpList.toList
    altList.toList
  }

  def buildFromAlternativesList(alternativesLists: List[List[ANode]], lexemes: SourceCodeLexemeReader, ast: AST): Option[List[Node]] = {
    for (alternatives <- alternativesLists) {
      val currOffset = lexemes.offset
      val res = new ArrayBuffer[Node]()
      var i = 0
      var isExistNone = false
      while (!isExistNone && i < alternatives.size) {
        val lastNodes = alternatives(i).build(lexemes, ast)
        i += 1
        isExistNone = lastNodes match {
          case Some(l) => res ++= l; false
          case _ => true
        }
      }
      if (!isExistNone)
        return Some(res.toList)
      else
        lexemes.offset = currOffset
    }
    None
  }

  def buildFromRule(lexemes: SourceCodeLexemeReader, ast: AST, rule: RuleAN): Option[RuleNode] = {
    buildFromAlternativesList(rule.riLists, lexemes, ast) match {
      case Some(ruleItems) => Some(new RuleNode(rule.name, ruleItems))
      case _ => None
    }
  }

  def buildFromComplex(lexemes: SourceCodeLexemeReader, ast: AST, rules: List[RuleAN]): Option[Node] = {
    for (rule <- rules) {
      buildFromRule(lexemes, ast, rule) match {
        case Some(ruleItem) => return Some(ruleItem)
        case _ =>
      }
    }
    None
  }
}
