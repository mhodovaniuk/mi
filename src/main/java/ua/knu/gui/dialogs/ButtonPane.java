package ua.knu.gui.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ButtonPane extends AnchorPane {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button okBtn;
    private CommonDialogController dialogController;

    public ButtonPane() {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/dialogs/buttonPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public Button getOkBtn() {
        return okBtn;
    }

    public void setDialogController(CommonDialogController dialogController) {
        this.dialogController = dialogController;
    }

    public void okClick(ActionEvent actionEvent) {
        dialogController.setCanceled(false);
    }

    public void cancelClick(ActionEvent actionEvent) {
        dialogController.setCanceled(true);
        dialogController.getDialog().close();
    }



}
