package ua.knu.gui.dialogs.newp;

import javafx.scene.layout.Pane;
import ua.knu.studio.Project;

public abstract class NewProjectMenuItem implements Comparable<NewProjectMenuItem> {
    protected String title;
    protected Pane pane;

    public NewProjectMenuItem(String title, Pane pane) {
        this.title=title;
        this.pane=pane;

    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewProjectMenuItem that = (NewProjectMenuItem) o;

        if (!title.equals(that.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public int compareTo(NewProjectMenuItem o) {
        return title.compareTo(o.title);
    }

    public Pane getPane() {
        return pane;
    }


    public abstract Project getProject();
}
