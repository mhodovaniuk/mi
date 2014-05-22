package ua.knu.common.utils

object FileUtils {
  def readFileContent(fileName:String):String={
    io.Source.fromFile(fileName).getLines().mkString("\n")
  }
}
