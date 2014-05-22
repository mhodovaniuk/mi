package ua.knu.gui.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.knu.studio.Project;

import java.io.IOException;

public class CommonDialog<T extends CommonDialogController> extends Stage {
    protected T controller;
    public CommonDialog(String fxmlFileName) {
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader=new FXMLLoader(getClass().getResource(fxmlFileName));
        try {
            setScene(new Scene(loader.load()));
            controller=(T)loader.getController();
            controller.setDialog(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Project getProject(){
        return controller.getProject();
    }
}
