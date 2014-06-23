package ua.knu.mi.st.types

case class Type(name:String, subTypes :Option[List[Type]]) {
  def this(name:String){
    this(name,None)
  }

  def isPrimitive=subTypes == None
}