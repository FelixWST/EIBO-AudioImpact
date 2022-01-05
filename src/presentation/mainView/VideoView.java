package presentation.mainView;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class VideoView extends MediaView {

    File file = new File("220105_Henkel-30sek-Final.mp4");

    public VideoView(){
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        this.setMediaPlayer(mediaPlayer);
    }

}
