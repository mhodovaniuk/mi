package ua.knu.gui

import scala.reflect.runtime.universe.typeOf
import scalafx.application.{Platform, JFXApp}
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.control.{ComboBox, TextField}
import scalafx.event.ActionEvent
import scalafxml.core.{DependenciesByType, FXMLView}
import scalafxml.core.macros.sfxml
import javafx.beans.binding.StringBinding

object Main extends JFXApp {

  val root = FXMLView(getClass.getResource("/mainframe.fxml"),new DependenciesByType(Map()))

  stage=new JFXApp.PrimaryStage(){
    title="MIlan Studio"
    scene=new Scene(root)
  }
}
