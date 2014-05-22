package ua.knu.gui.project;

import ua.knu.gui.project.listeners.SetProjectListener;
import ua.knu.studio.Project;

import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {
    private Project project;
    private List<SetProjectListener> setProjectListenerList=new ArrayList<>();
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        if (project!=null) {
            this.project = project;
            setProjectListenerList.forEach(l->l.onSetProject(project));
        }
    }

    public void addSetProjectListener(SetProjectListener listener){
        setProjectListenerList.add(listener);
    }

    private static ProjectController ourInstance = new ProjectController();

    public static ProjectController getInstance() {
        return ourInstance;
    }

    private ProjectController() {
    }
}
