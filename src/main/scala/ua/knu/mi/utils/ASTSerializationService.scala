package ua.knu.mi.utils

import java.io.{ByteArrayInputStream, ObjectInputStream, ObjectOutputStream, ByteArrayOutputStream}
import ua.knu.mi.ast.AST

object ASTSerializationService {
  def toByteArray(ast: AST): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(ast)
    oos.close()
    println(baos.toByteArray)
    baos.toByteArray
  }

  def fromByteArray(bytes: Array[Byte]): AST = {
    val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val res = ois.readObject().asInstanceOf[AST]
    ois.close()
    res
  }
}
