package presentation.mainView.exportView;

import business.managing.Project;
import business.managing.VideoFile;
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

        root.getProjectNameTextField().setText(project.getProjectTitle());
        root.getFileNameTextField().setText(project.getProjectTitle()+"-export.wav");

        root.getProjectNameTextField().focusedProperty().addListener(((observableValue, aBoolean, newValue) -> {
            if(newValue){
                System.out.println("Focused");
            }else{
                if(root.getProjectNameTextField().textProperty().get()!=""){
                    root.getProjectNameTextField().setId("");
                }else{
                    root.getProjectNameTextField().setId("error");
                }
                System.out.println("Focus left");
            }
        }));



        root.getOpenProject().setOnMouseClicked((event->{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("AudioImpact Project","*.aiprj"));
            File loadedFile = fc.showOpenDialog(root.getScene().getWindow());

            if(loadedFile!=null){
                if(loadedFile.isFile()){
                    System.out.println(loadedFile);
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
                    if(project!=null){
                        project.setExportPath(saveDirectory.getPath());
                    }
                }
            }
        }));

        root.getExportAudio().setOnMouseClicked((event -> {
            //Check ob überhaupt schon exportierbereit ist
        }));
    }

    public ExportView getRoot(){
        return this.root;
    }
}
