package business.managing;

import javafx.scene.media.Media;
import java.io.File;
import java.io.IOException;

public class VideoFile {
    private int framerate;
    private int frameTime;
    private File videoFile;
    private Media videoMedia;


    public VideoFile(File videoFile) throws IOException {
        this.videoFile = videoFile;
        if(videoFile.exists()){
            this.videoMedia = new Media(videoFile.toURI().toString());
        }else{
            throw new IOException("VideoFile is missing!");
        }
        this.framerate = 25;
        this.frameTime = 1000 / framerate;
    }

    public Media getVideoMedia(){
        return this.videoMedia;
    }

    public long getDuration(){
        return (long)this.videoMedia.getDuration().toMillis();
    }

    public File getVideoFile(){
        return this.videoFile;
    }
}
