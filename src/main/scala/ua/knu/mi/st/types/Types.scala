package ua.knu.mi.st.types

import scala.collection.mutable.HashMap

class Types(val types: List[Type]) {
  val typesHierarchy = buildTypeHierarchy()


  private def buildTypeHierarchy(): HashMap[String, List[String]] = {
    val resMap = new HashMap[String, List[String]]()
    def buildAllSubtypes(t: Type): List[String] = {
      if (resMap.get(t.name) == None)
        resMap += t.name -> (t.subTypes match {
          case Some(subTypes) =>
            subTypes.map(buildAllSubtypes).reduce(_ ++ _) ++ List(t.name)
          case None => List(t.name)
        })
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
      typesHierarchy.contains(typeName) && typesHierarchy(typeName).exists(_ == subTypeName)
    }
  }

}