package ua.knu.gui.dialogs.newp;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ua.knu.studio.Project;
import ua.knu.studio.ProjectFactory;

public class NewProjectMenuItemFromFiles extends NewProjectMenuItem {
    private final TextField lexerFileNameTF;
    private final TextField miFileNameTF;
    private final TextField sourceCodeFileNameTF;

    public NewProjectMenuItemFromFiles(GridPane pane, TextField lexerFileNameTF, TextField miFilePathTF, TextField sourceCodeFileNameTF) {
        super("New From Files",pane);
        this.lexerFileNameTF = lexerFileNameTF;
        this.miFileNameTF = miFilePathTF;
        this.sourceCodeFileNameTF = sourceCodeFileNameTF;
    }

    @Override
    public Project getProject() {
        return ProjectFactory.createProject(lexerFileNameTF.getText(),miFileNameTF.getText(),sourceCodeFileNameTF.getText());
    }
}
