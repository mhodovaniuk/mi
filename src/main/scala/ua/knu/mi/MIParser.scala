package ua.knu.mi

import ua.knu.mi.ast.inheritance._
import ua.knu.mi.ast.syntax._
import utils._
import ua.knu.mi.ast.{AProgram, AST}
import scala.util.parsing.combinator.RegexParsers
import ua.knu.mi.ast.syntax.QuantifierType.QuantifierType
import ua.knu.mi.ast.syntax.QuantifierType
import ua.knu.common.utils.FileUtils
import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

object MIParser {
  val parser: Parser = new Parser()
  val logger = Logger (LoggerFactory getLogger "MIParser")
  def parseConfigFile(configFileName: String): AST = {
    parseConfig(FileUtils.readFileContent(configFileName))
  }

  def parseConfig(config: String): AST = {
    new AST(parser.parseAll(parser.PROGRAM, config).get)
  }

  class Parser extends RegexParsers {
    val integer: Parser[String] = StringUtils.integer.r
    val real: Parser[String] = StringUtils.real.r
    val boolean: Parser[String] = StringUtils.boolean.r
    val identifier: Parser[String] = StringUtils.identifier.r
    val inheritance: Parser[String] = "INHERITANCE.".r
    val syntax: Parser[String] = "SYNTAX.".r
    val start: Parser[String] = "Start".r
    val token: Parser[String] = StringUtils.token.r
    val or: Parser[String] = "[|]".r

    def PROGRAM: Parser[AProgram] = {
      inheritance ~ INHERITANCE ~ syntax ~ SYNTAX ^^ {
        case _ ~ i ~ _ ~ s => {
          AProgram(i, s)
        }
      } | inheritance ~ INHERITANCE ^^ {
        case _ ~ i => new AProgram(i)
      }
    }

    def INHERITANCE: Parser[AInheritance] = {
      rep1(TYPE_DECLARATION ~ ".") ^^ {
        case typeDeclaration => {
          logger.debug(typeDeclaration.size.toString)
          AInheritance(typeDeclaration.map(_._1))
        }
      }
    }

    def TYPE_DECLARATION: Parser[ATypeDeclaration] = {
      TYPE ~ ":" ~ "<" ~ TYPE_LIST ~ ">" ^^ {
        case typeName ~ ":" ~ "<" ~ objTypeList ~ ">" => ComplexATypeDeclaration(typeName.name, objTypeList)
      }
    }

    //    def SEPARATOR: Parser[Any] = {
    //        "." | ";"
    //    }

    def TYPE: Parser[SimpleATypeDeclaration] = {
      identifier ^^ {
        SimpleATypeDeclaration
      }
    }

    def TYPE_LIST: Parser[List[ATypeDeclaration]] = {
      rep1sep(TYPE_ITEM, ";")
    }

    def TYPE_ITEM: Parser[ATypeDeclaration] = {
      TYPE_DECLARATION | TYPE
    }

    def SYNTAX: Parser[Option[ASyntax]] = {
      opt(start ~ "=" ~ identifier ~ "." ~ rep1(RULE)) ^^ {
        case Some(_ ~ _ ~ startRuleName ~ "." ~ rules) => Some(ASyntax(startRuleName, rules))
        case _ => None
      }
    }

    def RULE: Parser[RuleARI] = {
      identifier ~ "::=" ~ RI_LIST ~ ";" ^^ {
        case ruleName ~ _ ~ riList ~ _ => RuleARI(ruleName, RuleUtils.splitRuleOnAltRules(riList))
      }
    }

    def RI_LIST: Parser[List[ARuleItem]] = {
      rep1(RULE_ITEM)
    }

    def RULE_ITEM: Parser[ARuleItem] = {
      or ^^ {
        case orToken => new OrARI()
      } |
        ATTRIBUTE |
        CONSTR |
        identifier ^^ {
          IdentifierARI
        } |
        integer ^^ {
          case integerNumber => NumberARI(integerNumber.toInt)
        } |
        real ^^ {
          case realNumber => NumberARI(realNumber.toDouble)
        } |
        boolean ^^ {
          case boolVal => BooleanARI(boolVal.toBoolean)
        } |
        token ^^ {
          case tokenStr => TokenARI(StringUtils.removeBracketsAroundString(tokenStr))
        }
    }

    def ATTRIBUTE: Parser[AttributeARI] = {
      identifier ~ "=>" ~ identifier ^^ {
        case key ~ _ ~ value => AttributeARI(key, value)
      }
    }

    def CONSTR: Parser[QuantifierARI] = {
      "[" ~ RI_LIST ~ "]" ~ QUANTIFIER ~ token ^^ {
        case _ ~ rhList ~ _ ~ quantifier ~ separator => QuantifierARI(
          quantifier, RuleUtils.splitRuleOnAltRules(rhList), StringUtils.removeBracketsAroundString(separator)
        )
      }
    }

    def QUANTIFIER: Parser[QuantifierType] = {
      "+" ^^ {
        case "+" => QuantifierType.AT_LEAST_ONE
      } |
        "*" ^^ {
          case "*" => QuantifierType.ANY
        } |
        "!" ^^ {
          case "!" => QuantifierType.ONE_OR_ZERO
        }
    }
  }

}