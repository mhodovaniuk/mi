package ua.knu.gui.dialogs.openp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ua.knu.gui.dialogs.ButtonPane;
import ua.knu.gui.dialogs.CommonDialogController;
import ua.knu.gui.handlers.HandlersFactory;
import ua.knu.studio.Project;

import java.net.URL;
import java.util.ResourceBundle;

public class OpenProjectController extends CommonDialogController {
    @FXML
    private BorderPane dialogPane;
    @FXML
    private Button projectChooseFileBtn;
    @FXML
    private TextField projectFileNameTF;
    @FXML
    private ButtonPane buttonPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonPane.setDialogController(this);
        projectChooseFileBtn.setOnMouseClicked(HandlersFactory.createFileChooserButtonHandler("Choose project file","*.mip",projectFileNameTF));
    }

    @Override
    public Project getProject() {
        return null;
    }
}
