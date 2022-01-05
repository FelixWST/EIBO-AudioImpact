package presentation.mainView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.MediaPlayer;
import presentation.mainView.uicomponents.VideoControl;

public class VideoViewController {

    private VideoView root;
    private VideoPlayer videoPlayer;
    private VideoControl videoControl;

    public VideoViewController(){
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
                    break;
                case PAUSED:
                    videoPlayer.getMediaPlayer().play();
                    break;
                case PLAYING:
                    videoPlayer.getMediaPlayer().pause();
                    break;
            }
        });

        videoPlayer.getMediaPlayer().statusProperty().addListener(new ChangeListener<MediaPlayer.Status>() {
            @Override
            public void changed(ObservableValue<? extends MediaPlayer.Status> observableValue, MediaPlayer.Status status, MediaPlayer.Status t1) {
                switch(t1){
                    case PLAYING:
                        videoControl.getPlayButton().setText("Pause");
                        break;
                    default:
                        videoControl.getPlayButton().setText("Play");
                }
            }
        });


    }

    public VideoView getRoot(){
        return this.root;
    }
}
