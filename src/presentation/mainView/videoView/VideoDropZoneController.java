package presentation.mainView.videoView;

import business.managing.Project;
import business.managing.VideoFile;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
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
                    try{
                        project.setVideoFile(new VideoFile(loadedFile));
                    }catch (IOException e){

                    }
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
                    String[] extension = droppedFiles.get(0).getName().split("\\.");
                    if(extension[extension.length-1].equals("mp4")){
                        try{
                            project.setVideoFile(new VideoFile(droppedFiles.get(0)));
                        }catch (IOException e){

                        }
                    }
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
