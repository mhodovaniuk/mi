package ua.knu.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJavaFX extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        rootScene.getStylesheets().add("/css/textFlow.css");
        primaryStage.setTitle("MI Studio");
        primaryStage.setScene(rootScene);
        primaryStage.show();
    }
}
