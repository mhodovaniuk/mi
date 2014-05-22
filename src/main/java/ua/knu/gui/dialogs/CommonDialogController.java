package ua.knu.gui.dialogs;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import ua.knu.studio.Project;

public abstract class CommonDialogController implements Initializable{
    protected Stage dialog;
    protected boolean canceled=false;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Stage getDialog() {
        return dialog;
    }

    void setDialog(Stage dialog){
        this.dialog=dialog;
    }


    public abstract Project getProject();
}
