package presentation.mainView.timelineView;

import business.tracks.AudioTrackType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import presentation.application.Main;
import presentation.mainView.videoView.VideoViewController;

public class TimelineViewController {

    private StackPane root;
    private TimeLineView timeLineView;
    VideoViewController videoViewController;
    private Main application;

    public TimelineViewController(Main application, VideoViewController videoViewController){
        this.videoViewController = videoViewController;
        timeLineView = new TimeLineView();


        videoViewController.getMediaPlayer().setOnReady(()->{
            timeLineView.timelineTracks.timelineSlider.setMax(application.videoFile.getDuration());
        });

        timeLineView.timelineTracks.timelineSlider.valueChangingProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){
                videoViewController.getMediaPlayer().seek(new Duration(timeLineView.timelineTracks.timelineSlider.getValue()));
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



    }

    public Slider getTimelineSlider(){
        return this.timeLineView.timelineTracks.timelineSlider;
    }

    public StackPane getTimeLineView(){
        return this.root;
    }
}
