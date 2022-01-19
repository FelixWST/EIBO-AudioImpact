package presentation.mainView.videoView;

import business.managing.Project;
import business.managing.VideoFile;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class VideoDropZoneController {

    private VideoDropZone root;
    private Project project;

    public VideoDropZoneController(Project project){
        this.project = project;
        root = new VideoDropZone();

        root.getAddLabel().setOnMouseClicked((event -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".MP4 Video File","*.mp4"));
            File loadedFile = fc.showOpenDialog(root.getScene().getWindow());

            if(loadedFile!=null){
                if(loadedFile.isFile()){
                    project.setVideoFile(new VideoFile(loadedFile));
                }
            }
        }));

        root.getAddLabel().setOnDragOver((dragEvent -> {
            if(dragEvent.getGestureSource() != root.getAddLabel() && dragEvent.getDragboard().hasFiles()){
                dragEvent.acceptTransferModes(TransferMode.LINK);
            }
        }));

        root.getAddLabel().setOnDragDropped((dragEvent -> {
            Dragboard db = dragEvent.getDragboard();
            boolean success = false;

            if(db.hasFiles()){
                List<File> droppedFiles = db.getFiles();
                if(droppedFiles.size()==1){
                    success = true;
                    System.out.println(droppedFiles.get(0)+"CHECK DATATYPE");
                    project.setVideoFile(new VideoFile(droppedFiles.get(0)));
                }
            }
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
        }));

    }

    public VideoDropZone getRoot(){
        return this.root;
    }
}
