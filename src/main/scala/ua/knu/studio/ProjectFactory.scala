package ua.knu.studio

import ua.knu.mi.lexer.{SourceCodeLexemeReader, Lexer}
import ua.knu.mi.MIParser
import ua.knu.common.utils.FileUtils
import ua.knu.studio.Project

object ProjectFactory {
  def createProject(lexerConfigFileName:String,miConfigFileName:String,sourceCodeFileName:String):Project= {
    val lexer=new Lexer(lexerConfigFileName)
    val ast=MIParser.parseConfigFile(miConfigFileName)
    val miConfig=FileUtils.readFileContent(miConfigFileName)
    val sourceCode=FileUtils.readFileContent(sourceCodeFileName)
    val sclr = SourceCodeLexemeReader.createSourceCodeLexemeReaderFromSourceCodeFile(sourceCodeFileName,lexer)
    val st=ast.build(sclr)
    new Project(lexer,sclr,sourceCode,miConfig,ast,st)
  }
}
