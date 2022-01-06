package presentation.mainView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.MediaPlayer;
import presentation.application.Main;
import presentation.mainView.uicomponents.VideoControl;

public class VideoViewController {

    private VideoView root;
    private VideoPlayer videoPlayer;
    private VideoControl videoControl;
    private Main application;

    public VideoViewController(Main application){
        this.application = application;
        this.videoControl = new VideoControl();
        this.videoPlayer = new VideoPlayer();
        this.root = new VideoView(videoPlayer, videoControl);

        videoPlayer.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(videoPlayer.mediaView.getFitWidth());
            }
        });

        videoControl.getPlayButton().setOnAction((event)->{
            MediaPlayer.Status status = videoPlayer.getMediaPlayer().getStatus();

            switch(status){
                case READY:
                    videoPlayer.getMediaPlayer().play();
                    application.playerManager.startPlaying();
                    break;
                case PAUSED:
                    videoPlayer.getMediaPlayer().play();
                    application.playerManager.startPlaying();
                    break;
                case PLAYING:
                    videoPlayer.getMediaPlayer().pause();
                    application.playerManager.pausePlaying();
                    break;
            }
        });

        videoPlayer.getMediaPlayer().statusProperty().addListener(new ChangeListener<MediaPlayer.Status>() {
            @Override
            public void changed(ObservableValue<? extends MediaPlayer.Status> observableValue, MediaPlayer.Status status, MediaPlayer.Status t1) {
                switch(t1){
                    case PLAYING:
                        videoControl.getPlayButton().setId("pause-button");
                        break;
                    default:
                        videoControl.getPlayButton().setId("play-button");
                }
            }
        });


    }

    public VideoView getRoot(){
        return this.root;
    }
}
