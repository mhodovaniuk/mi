package ua.knu.gui.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class HandlersFactory {
    public static EventHandler<MouseEvent> createFileChooserButtonHandler(String dialogText, String extension, TextField filePathTF) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(dialogText);
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extension, extension));
                File chosenFile = fileChooser.showOpenDialog(null);
                if (chosenFile != null) {
                    filePathTF.setText(chosenFile.getPath());
                }
            }
        };
    }
}
