package presentation.mainView.videoView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import presentation.application.Main;
import presentation.mainView.uicomponents.VideoControl;

import java.util.concurrent.TimeUnit;

public class VideoViewController {

    private VideoView root;
    private VideoPlayer videoPlayer;
    private VideoControl videoControl;
    private VideoDropZoneController videoDropZoneController;
    private Main application;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    public VideoViewController(Main application){
        this.application = application;
        this.videoControl = new VideoControl();
        this.videoPlayer = new VideoPlayer();
        this.videoDropZoneController = new VideoDropZoneController();
        this.root = new VideoView(videoPlayer, videoDropZoneController.getRoot(), videoControl);

        mediaPlayer = new MediaPlayer(application.videoFile.getVideoMedia());
        mediaView = new MediaView(mediaPlayer);


        videoPlayer.setMediaView(mediaView);

        videoPlayer.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(mediaView.getFitWidth());
            }
        });

        videoControl.getPlayButton().setOnAction((event)->{
            MediaPlayer.Status status = mediaPlayer.getStatus();

            switch(status){
                case READY:
                    mediaPlayer.play();
                    application.playerManager.startPlaying();
                    break;
                case PAUSED:
                    mediaPlayer.play();
                    application.playerManager.startPlaying();
                    break;
                case PLAYING:
                    mediaPlayer.pause();
                    application.playerManager.pausePlaying();
                    break;
            }
        });

        mediaPlayer.statusProperty().addListener(new ChangeListener<MediaPlayer.Status>() {
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

        mediaPlayer.currentTimeProperty().addListener(((observableValue, duration, t1) -> {
            root.videoProgressBar.setProgress(t1.toMillis()/mediaPlayer.getMedia().getDuration().toMillis());
            videoControl.getTimeLabel().setText(millisToTimecode((long) t1.toMillis()));

            application.editingViewController.getTimelineViewController().getTimelineSlider().setValue(t1.toMillis());
        }));

        mediaPlayer.onEndOfMediaProperty().addListener(((observableValue, runnable, t1) -> {
            System.out.println("End Of Video");
        }));



    }

    public String millisToTimecode(long millis){
        return String.format("%02d:%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                (TimeUnit.MILLISECONDS.toMillis(millis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis)))/40);
    }

    public VideoView getRoot(){
        return this.root;
    }

    public MediaView getMediaView(){
        return this.mediaView;
    }

    public MediaPlayer getMediaPlayer(){
        return this.mediaPlayer;
    }
}
