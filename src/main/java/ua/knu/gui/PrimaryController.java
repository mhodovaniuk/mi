package ua.knu.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;
import ua.knu.gui.dialogs.newp.NewProject;
import ua.knu.gui.dialogs.openp.OpenProject;
import ua.knu.gui.project.ProjectController;
import ua.knu.studio.Project;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenuItem;
    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private TreeTableView tree;
    @FXML
    private TextFlow editor;
    @FXML
    private BorderPane primaryScene;
    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onClose(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onNewProjectClick(ActionEvent actionEvent) {
        NewProject newProjectDialog = new NewProject();
        newProjectDialog.showAndWait();
        Project project = newProjectDialog.getProject();
        if (project!=null)
            onNewProject(project);
    }

    private void onNewProject(Project project) {
        this.project=project;
    }

    public void onOpenProjectClick(ActionEvent actionEvent) {
        OpenProject openProjectDialog = new OpenProject();
        openProjectDialog.showAndWait();
    }
}
