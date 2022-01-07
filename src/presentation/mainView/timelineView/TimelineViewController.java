package presentation.mainView.timelineView;

import business.tracks.AudioTrackType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import presentation.application.Main;
import presentation.mainView.videoView.VideoViewController;

public class TimelineViewController {

    private StackPane root;
    private TimeLineView timeLineView;
    TimelineTimeIndicator timelineTimeIndicator;
    VideoViewController videoViewController;
    private Main application;

    public TimelineViewController(Main application, VideoViewController videoViewController){
        this.videoViewController = videoViewController;
        timeLineView = new TimeLineView();
        timelineTimeIndicator = new TimelineTimeIndicator();


        videoViewController.getMediaPlayer().setOnReady(()->{
            timelineTimeIndicator.timeIndicator.setMax(application.videoFile.getDuration());
        });

        timelineTimeIndicator.timeIndicator.valueChangingProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){
                videoViewController.getMediaPlayer().seek(new Duration(timelineTimeIndicator.timeIndicator.getValue()));
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

    public TimelineTimeIndicator getTimelineTimeIndicator(){
        return this.timelineTimeIndicator;
    }

    public StackPane getTimeLineView(){
        return this.root;
    }
}
