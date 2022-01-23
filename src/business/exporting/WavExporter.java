package business.exporting;

import business.editing.KeyframeManager;
import business.managing.Project;
import business.tracks.MergedTrack;

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

    public WavExporter(Project project, File exportDirectory){
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
                WavManipulator wavManipulator = new WavManipulator(kfm, trackLenghtInMs, videoLengthInMs);
                wavManipulator.readFile(mergedTrack.getAudioTrack(kfm.getAudioTrackType()).getWavPath());
                wavManipulator.writeFile(exportDirectory.getPath()+"/"+exportFileName+"-"+kfm.getAudioTrackType().name()+".wav");
            }
        }
    }
}
