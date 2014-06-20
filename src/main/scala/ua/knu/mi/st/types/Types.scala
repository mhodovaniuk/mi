package ua.knu.mi.st.types

import scala.collection.mutable.HashMap

class Types(val types: List[Type]) {
  val typesHierarchy = buildTypeHierarchy()


  private def buildTypeHierarchy(): HashMap[String, List[Type]] = {
    val resMap = new HashMap[String, List[Type]]()
    def buildAllSubtypes(t: Type, includeCurrentType: Boolean = false): List[Type] = {
      if (resMap.get(t.name) == None)
        resMap += t.name -> (t.subTypes match {
          case Some(subTypes) =>
            subTypes.map(buildAllSubtypes(_, includeCurrentType = true)).reduce(_ ++ _)
          case None => List()
        })
      if (includeCurrentType)
        resMap(t.name) ++ List(t)
      else
        resMap(t.name)
    }

    for (t <- types)
      buildAllSubtypes(t)
    resMap
  }

  def isSubtypeOf(subTypeName: String, typeName: String): Boolean = {
    if (typeName == subTypeName)
      true
    else {
      typesHierarchy(typeName).exists(_.name == subTypeName)
    }
  }

}