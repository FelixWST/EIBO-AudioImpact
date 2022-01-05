package presentation.mainView;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class VideoPlayer extends MediaViewPane {

    File file = new File("src/data/video/videoplayback.mp4");
    MediaView mediaView;
    public VideoPlayer(){
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaView = new MediaView(mediaPlayer);
        //mediaView.setPreserveRatio(true);
        this.setStyle("-fx-background-color: black");
        this.setMediaView(mediaView);
    }

}
