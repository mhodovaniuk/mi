package ua.knu.gui.dialogs.newp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import ua.knu.gui.dialogs.ButtonPane;
import ua.knu.gui.dialogs.CommonDialogController;
import ua.knu.gui.handlers.HandlersFactory;
import ua.knu.studio.Project;

import java.net.URL;
import java.util.ResourceBundle;

public class NewProjectController extends CommonDialogController {
    @FXML
    private BorderPane newDialogScene;
    @FXML
    private TextField lexerFileNameTF;
    @FXML
    private TextField sourceCodeFileNameTF;
    @FXML
    private TextField miFileNameTF;
    @FXML
    private Button lexerFileChooserB;
    @FXML
    private Button sourceCodeFileChooserB;
    @FXML
    private Button miFileChooserB;
    @FXML
    private GridPane newFromFilesPane;
    @FXML
    private ListView<NewProjectMenuItem> newProjectTypesList;
    @FXML
    private AnchorPane newProjectPane;
    @FXML
    private ButtonPane buttonPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lexerFileChooserB.setOnMouseClicked(HandlersFactory.
                createFileChooserButtonHandler( "Choose lexer configuration file", "*.json", lexerFileNameTF));
        sourceCodeFileChooserB.setOnMouseClicked(HandlersFactory.
                createFileChooserButtonHandler( "Choose source code file", "*.*", sourceCodeFileNameTF));
        miFileChooserB.setOnMouseClicked(HandlersFactory.
                createFileChooserButtonHandler("Choose MI configuration file", "*.mi", miFileNameTF));
        newProjectTypesList.getItems().add(new NewProjectMenuItemFromFiles(newFromFilesPane, lexerFileNameTF, miFileNameTF, sourceCodeFileNameTF));
        newProjectTypesList.getSelectionModel().select(0);
        buttonPane.setDialogController(this);
    }

    @Override
    public Project getProject() {
        if (canceled)
            return null;
        else return newProjectTypesList.getSelectionModel().getSelectedItems().get(0).getProject();
    }
}
