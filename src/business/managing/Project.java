package business.managing;

import business.editing.KeyframeManager;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.MergedTrack;

import java.util.ArrayList;

public class Project {

    private String projectTitle;
    private String fileName;
    private String path;
    private ArrayList<KeyframeManager> keyframeManagers;
    private MergedTrack mergedTrack;
    private VideoFile videoFile;

    public Project(){
        this("unnamed Project", "unnamedProject.prj", "path/to/proj", null);
    }

    public Project(String projectTitle, String fileName, String path, MergedTrack mergedTrack) {
        this(projectTitle, fileName, path, mergedTrack, null);
    }

    public Project(String projectTitle, String fileName, String path, MergedTrack mergedTrack, VideoFile videoFile) {
        this.projectTitle = projectTitle;
        this.fileName = fileName;
        this.path = path;
        this.mergedTrack = mergedTrack;
        this.keyframeManagers = new ArrayList<>();
        this.videoFile = videoFile;
        createKeyframeManagers();
    }

    private void createKeyframeManagers(){
        if(mergedTrack!=null){
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
        if(this.videoFile == null && videoFile != null){
            this.videoFile = videoFile;
        }
    }

    public VideoFile getVideoFile(){
        return this.videoFile;
    }

    public String getProjectTitle(){
        return this.projectTitle;
    }


}
