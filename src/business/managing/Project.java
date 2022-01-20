package business.managing;

import business.editing.Keyframe;
import business.editing.KeyframeManager;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.MergedTrack;
import javafx.beans.property.SimpleObjectProperty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Project {

    private String projectTitle;
    private String fileName;
    private String exportPath;
    private ArrayList<KeyframeManager> keyframeManagers;
    private MergedTrack mergedTrack;
    private SimpleObjectProperty<VideoFile> videoFileProperty;


    public Project(){
        this("unnamed Project", "unnamedProject.prj", "path/to/proj", null);
    }

    public Project(String projectTitle, String fileName, String exportPath) {
        this(projectTitle, fileName, exportPath, null, null);
    }

    public Project(String projectTitle, String fileName, String exportPath, MergedTrack mergedTrack) {
        this(projectTitle, fileName, exportPath, mergedTrack, null);
    }

    public Project(String projectTitle, String fileName, String exportPath, MergedTrack mergedTrack, VideoFile videoFile) {
        this.projectTitle = projectTitle;
        this.fileName = fileName;
        this.exportPath = exportPath;
        this.mergedTrack = mergedTrack;
        this.keyframeManagers = new ArrayList<>();
        this.videoFileProperty = new SimpleObjectProperty<>(videoFile);
        createKeyframeManagers();
    }

    private void createKeyframeManagers(){
        if(mergedTrack!=null){
            keyframeManagers = new ArrayList<>();
            for(AudioTrack audioTrack : mergedTrack.getAudioTracks()){
                if(audioTrack!=null){
                    keyframeManagers.add(new KeyframeManager(audioTrack.getAudioTrackType()));
                }
            }
        }
    }

    public KeyframeManager getKeyframeManager(AudioTrackType audioTrackType){
        for(KeyframeManager keyframeManager : keyframeManagers){
            if(keyframeManager.getAudioTrackType() == audioTrackType){
                return keyframeManager;
            }
        }
        return null;
    }

    public ArrayList<KeyframeManager> getKeyframeManagers(){
        return this.keyframeManagers;
    }

    public void setMergedTrack(MergedTrack newMergedTrack){
        if(newMergedTrack != null){
            if(mergedTrack!=null){
                if(!mergedTrack.equals(newMergedTrack)){
                    mergedTrack = newMergedTrack;
                }
            }else{
                mergedTrack = newMergedTrack;
                createKeyframeManagers();
            }
        }
    }

    public MergedTrack getMergedTrack(){
        return this.mergedTrack;
    }

    public void setVideoFile(VideoFile videoFile){
        if(videoFile != null){
            this.videoFileProperty.set(videoFile);
        }
    }

    public SimpleObjectProperty<VideoFile> videoFileProperty(){
        return this.videoFileProperty;
    }

    public String getProjectTitle(){
        return this.projectTitle;
    }

    public int findPreviousKeyframeTime(int millis){
        int timeDelta = Integer.MAX_VALUE;
        Keyframe closest = new Keyframe(0,0);

        for(KeyframeManager kfm : keyframeManagers){
            for(int i = 0; i<kfm.getKeyframes().size(); i++){
                if(kfm.getKeyframes().get(i).getTime() < millis){
                    if(millis - kfm.getKeyframes().get(i).getTime() < timeDelta){
                        closest = kfm.getKeyframes().get(i);
                        timeDelta = millis - kfm.getKeyframes().get(i).getTime();
                    }
                }
            }
        }
        return closest.getTime();
    }

    public int findNextKeyframeTime(int millis){
        int timeDelta = Integer.MAX_VALUE;
        Keyframe closest = new Keyframe((int) videoFileProperty.get().getDuration(), 0);

        for(KeyframeManager kfm : keyframeManagers){
            for(int i = 0; i<kfm.getKeyframes().size(); i++){
                if(kfm.getKeyframes().get(i).getTime() > millis){
                    if(kfm.getKeyframes().get(i).getTime() - millis < timeDelta){
                        closest = kfm.getKeyframes().get(i);
                        timeDelta = kfm.getKeyframes().get(i).getTime()-millis;
                    }
                }
            }
        }
        return closest.getTime();
    }

    public void saveProject(File directory) throws IOException {
        String fileHeader, fileBody;
        if(directory!=null){

            BufferedWriter writer = new BufferedWriter(new FileWriter(directory));



            fileHeader =  "projectTitle="+projectTitle+"\n";
            fileHeader += "fileName="+fileName+"\n";
            fileHeader += "path="+directory+"\n";
            if(mergedTrack!=null){
                fileHeader += "mergedTrack="+mergedTrack.getTitle()+"\n";
            }
            if(videoFileProperty.get()!=null){
                fileHeader += "videoFile="+videoFileProperty.get().getVideoFile()+"\n";
            }
            fileHeader += "\n\n";


            fileBody = "#BODY\n";
            if(keyframeManagers!=null){
                for(KeyframeManager kfm : keyframeManagers){
                    if(kfm!=null){
                        fileBody += "audioTrackType="+kfm.getAudioTrackType()+"\n";
                        fileBody += "#BEGINKF\n";
                        if(kfm.getKeyframes()!=null){
                            for(Keyframe kf : kfm.getKeyframes()){
                                fileBody += kf.getTime()+"|"+kf.getVolume()+";";
                            }
                        }
                        fileBody += "\n#ENDKF\n";
                    }
                }
            }

            writer.write(fileHeader+fileBody);
            writer.close();

        }
    }

    public void loadFromProject(){

    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }
}