package ua.knu.mi.st.rules

import scala.collection.mutable

trait RuleItem {
  val attributes=mutable.HashMap[String,Any]()
}
