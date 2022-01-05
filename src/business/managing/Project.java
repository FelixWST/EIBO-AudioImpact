package business.managing;

import business.editing.TrackEditor;

public class Project {

    private String projectTitle;
    private String fileName;
    private String path;
    private TrackEditor trackEditor;
    private TrackManager trackManager;

    public Project(String projectTitle, String fileName, String path, TrackEditor trackEditor, TrackManager trackManager) {
        this.projectTitle = projectTitle;
        this.fileName = fileName;
        this.path = path;
        this.trackEditor = trackEditor;
        this.trackManager = trackManager;
    }


}
