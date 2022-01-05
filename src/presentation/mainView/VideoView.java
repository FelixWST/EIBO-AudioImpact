package presentation.mainView;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import presentation.mainView.uicomponents.VideoControl;

import java.io.File;

public class VideoView extends VBox {

    File file = new File("src/data/video/videoplayback.mp4");

    public VideoView(){
        MediaView mediaView = new MediaView();
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);

        this.getChildren().addAll(mediaView);
    }

}
