package business.exporting;

import business.editing.KeyframeManager;
import business.managing.Project;
import business.tracks.MergedTrack;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.util.ArrayList;

public class WavExporter {

    private Project project;
    private ArrayList<KeyframeManager> keyframeManagers;
    private long trackLenghtInMs;
    private long videoLengthInMs;
    private MergedTrack mergedTrack;
    private String exportFileName;
    private File exportDirectory;
    private boolean debug;

    public WavExporter(Project project, File exportDirectory){
        this.debug = false;
        this.project = project;
        this.exportDirectory = exportDirectory;

        if(project!=null){
            if(project.getKeyframeManagers()!=null){
                this.keyframeManagers = project.getKeyframeManagers();
            }
            if(project.mergedTrackProperty().get()!=null){
                this.mergedTrack = project.mergedTrackProperty().get();
            }
            if(this.mergedTrack!=null){
                this.trackLenghtInMs = mergedTrack.getDuration()*1000;
            }
            if(project.videoFileProperty().get()!=null){
                this.videoLengthInMs = project.videoFileProperty().get().getDuration();
            }
            if(project.getFileName()!=""){
                this.exportFileName = project.getFileName();
            }else{
                this.exportFileName = "export";
            }
        }
        if(this.exportDirectory == null || !this.exportDirectory.isDirectory()){
            this.exportDirectory = new File("./output");
        }
    }

    public void export(){
        //Export works with Mono 16Bit
        if(project != null && keyframeManagers != null && trackLenghtInMs != 0 && videoLengthInMs != 0 && mergedTrack != null){
            for(KeyframeManager kfm : keyframeManagers) {
                WavManipulator wavManipulator = new WavManipulator(kfm, trackLenghtInMs, videoLengthInMs,debug);
                wavManipulator.readFile(mergedTrack.getAudioTrack(kfm.getAudioTrackType()).getWavPath());
                wavManipulator.writeFile(exportDirectory.getPath()+"/"+exportFileName+"-"+mergedTrack.getTitle()+"-"+kfm.getAudioTrackType().name()+".wav");
            }

            File[] directoryList = exportDirectory.listFiles();
            ArrayList<Boolean> result = new ArrayList<>();
            if(directoryList!=null){
                for(KeyframeManager kfm : keyframeManagers){
                    for(File f : directoryList){
                        if(f.equals(new File(exportDirectory.getPath()+"/"+exportFileName+"-"+mergedTrack.getTitle()+"-"+kfm.getAudioTrackType().name()+".wav"))){
                            result.add(true);
                        }
                    }
                }
                if(result.size() == keyframeManagers.size()){
                    Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Export successfull!");
                                alert.setHeaderText("Your Audiofiles have been exported to: "+exportDirectory.getPath());
                                alert.showAndWait();
                            }
                    );
                }else{
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Export Unsuccsessful");
                        alert.setHeaderText("Something went wrong while exporting...");
                        alert.showAndWait();
                        }
                    );
                }
            }
        }
    }
}
