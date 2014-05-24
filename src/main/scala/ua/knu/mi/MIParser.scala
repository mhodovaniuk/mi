package ua.knu.mi

import ua.knu.mi.ast.inheritance._
import ua.knu.mi.ast.syntax._
import utils._
import ua.knu.mi.ast.{AProgram, AST}
import scala.util.parsing.combinator.RegexParsers
import ua.knu.mi.ast.syntax.QuantifierType.QuantifierType
import ua.knu.mi.ast.syntax.QuantifierType
import ua.knu.common.utils.FileUtils

object MIParser {
  val parser: Parser = new Parser()
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

    def RULE: Parser[RuleAN] = {
      identifier ~ "::=" ~ RI_LIST ~ ";" ^^ {
        case ruleName ~ _ ~ riList ~ _ => RuleAN(ruleName, RuleUtils.splitRuleOnAltRules(riList))
      }
    }

    def RI_LIST: Parser[List[ANode]] = {
      rep1(RULE_ITEM)
    }

    def RULE_ITEM: Parser[ANode] = {
      or ^^ {
        case orToken => new OrAN()
      } |
        ATTRIBUTE |
        CONSTR |
        identifier ^^ {
          IdentifierAN
        } |
        integer ^^ {
          case integerNumber => NumberAN(integerNumber.toInt)
        } |
        real ^^ {
          case realNumber => NumberAN(realNumber.toDouble)
        } |
        boolean ^^ {
          case boolVal => BooleanAN(boolVal.toBoolean)
        } |
        token ^^ {
          case tokenStr => TokenAN(StringUtils.removeBracketsAroundString(tokenStr))
        }
    }

    def ATTRIBUTE: Parser[AttributeAN] = {
      identifier ~ "=>" ~ identifier ^^ {
        case key ~ _ ~ value => AttributeAN(key, value)
      }
    }

    def CONSTR: Parser[QuantifierAN] = {
      "[" ~ RI_LIST ~ "]" ~ QUANTIFIER ~ token ^^ {
        case _ ~ rhList ~ _ ~ quantifier ~ separator => QuantifierAN(
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