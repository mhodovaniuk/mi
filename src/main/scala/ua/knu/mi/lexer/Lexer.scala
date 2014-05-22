package ua.knu.mi.lexer

import scala.collection.immutable.StringOps
import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex
import scala.collection.mutable.ArrayBuffer
import ua.knu.common.utils.FileUtils

class Lexer(configFileName: String) {
  var errorClassName=new String
  var recoveryClasses=List[String]()
  var caseSensitive=false
  var ignoreClasses = List[String]()
  var keywords: List[String] = List()
  var lexemesDescription = List[(String, Regex)]()
  init(configFileName)

  private def init(fileName: String) {
    val jsonText = FileUtils.readFileContent(fileName)
    val json = util.parsing.json.JSON.parseFull(jsonText)
    json match {
      case Some(m: Map[String, Any]) => {
        m("keywords") match {
          case kw: List[String] => keywords = kw
        }

        m("patterns") match {
          case sl: List[Map[String, String]] => {
            lexemesDescription =
              for (m <- sl) yield (m.get("class").get, m.get("pattern").get.r)
          }
        }

        m("error") match {
          case err: Map[String, Any] => {
            err("class-name") match {
              case cn:String=>errorClassName=cn
            }

            err("recovery-classes") match {
              case rc:List[String]=>recoveryClasses=rc
            }

          }
        }

        m("ignore") match {
          case l: List[String] => ignoreClasses = l
        }

        m("case-sensitive") match {
          case b:Boolean=> caseSensitive=b
        }

        m("case-sensitive") match {
          case b:Boolean=> caseSensitive=b
        }
      }
    }
  }


  private def nextError(sc:StringOps):Lexeme={
    def nextError(sc:StringOps):String={
      nextLexeme(sc) match {
        case None => if (sc.length==0) "" else sc(0)+nextError(sc.drop(1))
        case Some(l) => {
          recoveryClasses.find(_==l.className)
          ""
        }
      }
    }
    new Lexeme(nextError(sc),errorClassName,caseSensitive)
  }

  private def nextLexeme(sc: StringOps): Option[Lexeme] = {
    def findPrefixByRegexp(className: String, prefixPattern: Regex): Option[Lexeme] = {
      prefixPattern.findPrefixOf(sc) match {
        case Some(l) => Some(new Lexeme(l, className,caseSensitive))
        case None => None
      }
    }
    def findPrefixByKeyword(className: String, keyword: String): Option[Lexeme] = {
      if (keyword.length>sc.length)
        None
      else {
        for (i<-0 until keyword.length)
          if (sc(i).toUpper!=keyword(i).toUpper)
            return None
        return Some(new Lexeme(keyword,className,caseSensitive))
      }
    }
    for (kw <- keywords)
      findPrefixByKeyword("keyword", kw) match {
        case Some(l) => return Some(l)
        case _ =>
      }
    for (ld <- lexemesDescription)
      findPrefixByRegexp(ld._1, ld._2) match {
        case Some(l) => return Some(l)
        case _ =>
      }
    None
  }

  def parse(sourceCode: StringOps): ArrayBuffer[Lexeme] = {
    var sc=sourceCode
    val lexemes = new ArrayBuffer[Lexeme]
    while (sc.length != 0) {
      val lex= nextLexeme(sc) match {
        case Some(l) => l
        case None => nextError(sc)
      }
      lexemes += lex
      sc=sc.drop(lex.value.length)
    }
    lexemes
  }
}
