package ua.knu.mi.st.types

import scala.collection.mutable.HashMap

class Types(val types:List[Type]){
  val typesHierarchy = buildTypeHierarchy()


  private def getAllSubtypes(t:Type,includeCurrentT:Boolean = false):List[Type]={
    t.subTypes match {
      case Some(subTypes)=> {
        (if (includeCurrentT)
          List(t)
        else
          List[Type]()) ++ subTypes.map(getAllSubtypes(_,true)).reduce(_++_)
      }
      case None => List(t)
    }
  }

  private def buildTypeHierarchy():HashMap[String,List[Type]] ={
    val resMap=new HashMap[String,List[Type]]()
    for (t<-types)
      resMap += t.name->getAllSubtypes(t)
    resMap
  }

  def isSubtypeOf(subTypeName: String, typeName: String): Boolean = {
    if (typeName==subTypeName)
      true
    else {
      typesHierarchy(typeName).exists(_.name==subTypeName)
    }
  }

}