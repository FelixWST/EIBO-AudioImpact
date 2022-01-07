package presentation.mainView.timelineView;

import business.tracks.AudioTrackType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import presentation.application.Main;
import presentation.mainView.videoView.VideoViewController;

import java.util.concurrent.TimeUnit;

public class TimelineViewController {

    private StackPane root;
    private TimeLineView timeLineView;
    VideoViewController videoViewController;
    private Main application;

    public TimelineViewController(Main application, VideoViewController videoViewController){
        this.videoViewController = videoViewController;
        timeLineView = new TimeLineView();


        videoViewController.getMediaPlayer().setOnReady(()->{
            timeLineView.timelineTracks.timelineSlider.setMax(videoViewController.getMediaPlayer().getTotalDuration().toMillis());
        });

        timeLineView.timelineTracks.timelineSlider.valueChangingProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){

                Duration dt = Duration.millis(timeLineView.timelineTracks.timelineSlider.getValue());
                videoViewController.getMediaPlayer().seek(dt);
            }
        }));



        root = new StackPane();
        //root.getChildren().addAll(timeLineView, timelineTimeIndicator);
        root.getChildren().addAll(timeLineView);

        timeLineView.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                timeLineView.timelineTracks.tl1.kf1.setRadius(timeLineView.timelineTracks.tl1.getHeight()/10);
            }
        });

        timeLineView.timelineTrackSettings.tls1.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            application.playerManager.setTrackVolume(AudioTrackType.DEPTH, t1.floatValue());
        }));

        timeLineView.timelineTrackSettings.tls2.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            application.playerManager.setTrackVolume(AudioTrackType.ATMOSPHERE, t1.floatValue());
        }));

        timeLineView.timelineTrackSettings.tls3.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            application.playerManager.setTrackVolume(AudioTrackType.INTENSITY, t1.floatValue());
        }));

        timeLineView.timelineTrackSettings.tls1.mute.setOnAction((event)->{

        });

        timeLineView.timelineTrackSettings.tls2.mute.setOnAction((event)->{

        });

        timeLineView.timelineTrackSettings.tls3.mute.setOnAction((event)->{

        });


        //SOLO -> DE-SOLO oder solo in Playermanager?
        timeLineView.timelineTrackSettings.tls1.solo.setOnAction((event)->{

        });

        timeLineView.timelineTrackSettings.tls2.solo.setOnAction((event)->{

        });

        timeLineView.timelineTrackSettings.tls3.solo.setOnAction((event)->{

        });

        timeLineView.timelineTracks.timelineSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                return millisToTimecode(aDouble.longValue());
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });



    }

    public Slider getTimelineSlider(){
        return this.timeLineView.timelineTracks.timelineSlider;
    }

    public StackPane getTimeLineView(){
        return this.root;
    }

    public String millisToTimecode(long millis){
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
