package ua.knu.gui;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import scala.Tuple2;
import scala.collection.Iterator;
import ua.knu.common.utils.JavaFXUtils;
import ua.knu.gui.components.LexemeText;
import ua.knu.gui.dialogs.newp.NewProject;
import ua.knu.gui.dialogs.openp.OpenProject;
import ua.knu.mi.lexer.Lexeme;
import ua.knu.mi.st.nodes.Node;
import ua.knu.mi.st.nodes.Node$;
import ua.knu.studio.Project;
import scala.collection.mutable.HashMap;
import ua.knu.studio.ProjectFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableColumn<Tuple2<String,Object>,String> valueColumn;
    @FXML
    private TableColumn<Tuple2<String,Object>,String> attributeColumn;
    @FXML
    private TreeView<Node> syntaxTreeView;
    @FXML
    private TableView<Tuple2<String,Object>> attributesTableView;
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
    private TextFlow editor;
    @FXML
    private BorderPane primaryScene;
    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attributeColumn.prefWidthProperty().bind(attributesTableView.widthProperty().multiply(0.5));
        valueColumn.prefWidthProperty().bind(attributesTableView.widthProperty().multiply(0.5));
        syntaxTreeView.setOnMouseClicked(getSyntaxTreeMouseHandler());
        syntaxTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        attributeColumn.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<Tuple2<String, Object>, String>, ObservableValue<String>>) param
                        -> new ReadOnlyStringWrapper(param.getValue()._1()));
        valueColumn.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<Tuple2<String, Object>, String>, ObservableValue<String>>) param
                        -> new ReadOnlyStringWrapper(param.getValue()._2().toString()));

        onNewProject(ProjectFactory.createProject("sql.json","sql.mi","sourcecode.sql"));
//        onNewProject(ProjectFactory.createProject("/home/mykhailo/WorkSpace/scala/mi/sql.json","/home/mykhailo/WorkSpace/scala/mi/dbd.mi","/home/mykhailo/WorkSpace/scala/mi/query.dbd"));
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
        syntaxTreeView.setRoot(JavaFXUtils.getProjectAsFXTree(project));
        //TODO:optimize higlighting
        editor.getChildren().clear();
        editor.getChildren().addAll(JavaFXUtils.getLexemesAsFXText(project));
    }

    public void onOpenProjectClick(ActionEvent actionEvent) {
        OpenProject openProjectDialog = new OpenProject();
        openProjectDialog.showAndWait();
    }

    private EventHandler<MouseEvent> getSyntaxTreeMouseHandler(){
        return new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                TreeItem<Node> selectedItem = (TreeItem<Node>) ((TreeView) event.getSource()).getSelectionModel().getSelectedItem();
                if (selectedItem!=null) {
                    Node selectedNode = selectedItem.getValue();
                    updateAttributeTable(selectedNode.visibleAttributes());
                    updateEditor(selectedNode);
                }
            }
        };
    }

    private void updateEditor(Node selectedItem) {
        int first = selectedItem.firstLexeme().id();
        int last = selectedItem.lastLexeme().id();
        for (javafx.scene.Node node : editor.getChildren()) {
            ((LexemeText)node).updateWithSelectionRange(first,last);
        }
    }

    private void updateAttributeTable(HashMap<String,Object> attributes) {
        attributesTableView.getItems().clear();
        Iterator<Tuple2<String, Object>> iterator = attributes.iterator();
        while (iterator.hasNext()){
            attributesTableView.getItems().add(iterator.next());
        }
    }
}
