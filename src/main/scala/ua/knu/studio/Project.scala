package ua.knu.studio

import ua.knu.mi.lexer._
import ua.knu.mi.ast.AST
import ua.knu.mi.st.ST

class Project(val lexer:Lexer,val sourceCode:String,val miConfig:String,val ast:AST,val st:ST) {

}
