package ua.knu.mi.utils

object StringUtils {
  val real = "0.[0-9]+"
  val integer = "[0-9]+"
  val boolean = "true|false"
  val identifier = "[A-Za-z_]+[A-Za-z_0-9-]*"
  val token = "\"[a-zA-Z0-9_ :=><,.+-/*();!&?\n\t]*\""

  def removeBracketsAroundString(str:String)={
    if (str.length<2)
      throw new IllegalArgumentException()
    str.substring(1,str.length-1)
  }
}
