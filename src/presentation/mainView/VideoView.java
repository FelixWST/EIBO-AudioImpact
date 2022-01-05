package presentation.mainView;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class VideoView extends MediaView {

    File file = new File("C:\\Users\\nikla\\Downloads\\The Breathtaking Beauty of Nature _ HD.mp4");

    public VideoView(){
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        this.setMediaPlayer(mediaPlayer);
    }

}
