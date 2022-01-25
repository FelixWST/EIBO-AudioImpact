package presentation.mainView.exportView;

import business.exporting.WavExporter;
import business.managing.Project;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class ExportViewController {
    private ExportView root;
    private Project project;

    public ExportViewController(Project project){
        this.project = project;
        this.root = new ExportView();

        root.getProjectNameTextField().setText(project.getProjectTitleProperty().get());
        root.getFileNameTextField().setText(project.getFileName());
        root.getPath().setText(project.getExportPath());

        root.getProjectNameTextField().focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if(!newValue){ //Focus left
                if(root.getProjectNameTextField().getText()!=""){
                    root.getProjectNameTextField().setId("");
                    project.getProjectTitleProperty().set(root.getProjectNameTextField().getText());
                }else{
                    root.getProjectNameTextField().setId("error");
                }
            }
        }));

        root.getFileNameTextField().focusedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){ //Focus left
                if(root.getFileNameTextField().getText()!=""){
                    root.getFileNameTextField().setId("");
                    project.setFileName(root.getFileNameTextField().getText());
                }else{
                    root.getFileNameTextField().setId("error");
                }
            }
        }));

        root.getOpenProject().setOnMouseClicked((event->{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("AudioImpact Project","*.aiprj"));
            File loadedFile = fc.showOpenDialog(root.getScene().getWindow());

            if(loadedFile!=null){
                if(loadedFile.isFile()){
                    try {
                        project.loadFromProject(loadedFile);
                        root.getProjectNameTextField().setText(project.getProjectTitleProperty().get());
                        root.getFileNameTextField().setText(project.getFileName());
                        root.getPath().setText(project.getExportPath());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));


        root.getSaveProject().setOnMouseClicked((event->{
            FileChooser sfc = new FileChooser();
            sfc.getExtensionFilters().add(new FileChooser.ExtensionFilter("AudioImpact Project","*.aiprj"));
            File saveFile = sfc.showSaveDialog(root.getScene().getWindow());
            if(saveFile!=null){
                try {
                    if(project!=null){
                        project.saveProject(saveFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));

        root.getDirectory().setOnMouseClicked((event -> {
            DirectoryChooser dc = new DirectoryChooser();
            File saveDirectory = dc.showDialog(root.getScene().getWindow());
            if(saveDirectory != null){
                if(saveDirectory.isDirectory() && saveDirectory.exists()){
                    root.getPath().setText(saveDirectory.getPath());
                    root.getPath().setId("");
                    if(project!=null){
                        project.setExportPath(saveDirectory.getPath());
                    }
                }
            }
        }));

        root.getExportAudio().setOnMouseClicked((event -> {
            if(root.getPath().getText()!="" && root.getFileNameTextField().getText()!=""){
                root.getPath().setId("");
                root.getFileNameTextField().setId("");
                File exportDirectory = new File(root.getPath().getText());
                if(exportDirectory.isDirectory()){
                    root.getPath().setId("");

                    if(project!=null && project.getKeyframeManagers() != null && project.videoFileProperty().get() != null && project.mergedTrackProperty().get() != null && project.getFileName() != ""){
                        WavExporter wavExporter = new WavExporter(project, exportDirectory);
                        wavExporter.export();
                    }

                }else{
                    root.getPath().setId("error");
                }
            }else{
                if(root.getPath().getText()==""){
                    root.getPath().setId("error");
                }else{
                    root.getPath().setId("");
                }
                if(root.getFileNameTextField().getText()==""){
                    root.getFileNameTextField().setId("error");
                }else{
                    root.getFileNameTextField().setId("");
                }
            }
        }));
    }

    public ExportView getRoot(){
        return this.root;
    }
}