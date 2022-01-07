package business.managing;

import javafx.scene.media.Media;

import java.io.File;

public class VideoFile {
    private int framerate;
    private int frameTime;
    private File videoFile;
    private Media videoMedia;


    public VideoFile(File videoFile){
        this.videoFile = videoFile;
        //File existing?
        this.videoMedia = new Media(videoFile.toURI().toString());
        this.framerate = 25;
        this.frameTime = 1000 / framerate;
    }

    public Media getVideoMedia(){
        return this.videoMedia;
    }

    public long getDuration(){
        return (long)this.videoMedia.getDuration().toMillis();
    }
}
