package presentation.mainView.timelineView;

import business.editing.Keyframe;
import business.editing.KeyframeManager;
import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import business.tracks.AudioTrackType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import presentation.mainView.EditingViewController;
import presentation.mainView.videoView.VideoViewController;

import java.util.concurrent.TimeUnit;

public class TimelineViewController {

    private TimeLineView root;
    private EditingViewController editingViewController;
    private Project project;
    private PlayerManager playerManager;
    private TrackManager trackManager;
    private VideoViewController videoViewController;

    public TimelineViewController(EditingViewController editingViewController, Project project, PlayerManager playerManager, TrackManager trackManager){
        this.editingViewController = editingViewController;
        this.project = project;
        this.playerManager = playerManager;
        this.trackManager = trackManager;
        this.videoViewController = editingViewController.getVideoViewController();
        this.root = new TimeLineView();


        videoViewController.getMediaPlayer().setOnReady(()->{
            root.timelineTracks.timelineSlider.setMax(videoViewController.getMediaPlayer().getTotalDuration().toMillis());
        });

        root.timelineTracks.timelineSlider.valueChangingProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){

                Duration dt = Duration.millis(root.timelineTracks.timelineSlider.getValue());
                videoViewController.getMediaPlayer().seek(dt);
            }
        }));



        //root.getChildren().addAll(timeLineView, timelineTimeIndicator);

        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                root.timelineTracks.tl1.kf1.setRadius(root.timelineTracks.tl1.getHeight()/10);
            }
        });

        root.timelineTrackSettings.tls1.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            playerManager.setTrackVolume(AudioTrackType.DEPTH, t1.floatValue());
        }));

        root.timelineTrackSettings.tls2.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            playerManager.setTrackVolume(AudioTrackType.ATMOSPHERE, t1.floatValue());
        }));

        root.timelineTrackSettings.tls3.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            playerManager.setTrackVolume(AudioTrackType.INTENSITY, t1.floatValue());
        }));

        root.timelineTrackSettings.tls1.mute.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls2.mute.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls3.mute.setOnAction((event)->{

        });


        //SOLO -> DE-SOLO oder solo in Playermanager?
        root.timelineTrackSettings.tls1.solo.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls2.solo.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls3.solo.setOnAction((event)->{

        });

        root.timelineTracks.timelineSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                return millisToTimecode(aDouble.longValue());
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });

        root.timelineTracks.tl1.widthProperty().addListener(((observableValue, number, t1) -> {
            for(KeyframeManager keyframeManager : project.getKeyframeManagers()){
                testDrawKeyframes(keyframeManager);
            }
        }));





    }

    public void testDrawKeyframes(KeyframeManager keyframeManager){
        long totalduration = project.getVideoFile().getDuration();
        double timelineWidth = root.timelineTracks.tl1.getWidth();

        double pxPerMs = (timelineWidth / totalduration);
        System.out.println("Duration: "+totalduration);
        System.out.println("PX Width: "+timelineWidth);
        System.out.println("Pixel pro Ms: "+pxPerMs);

        for(Keyframe kf : keyframeManager.getKeyframes()){
            Circle keyFrameCircle = new Circle();
            keyFrameCircle.setRadius(10);
            keyFrameCircle.setCenterX(pxPerMs*kf.getTime());
            root.timelineTracks.tl1.getChildren().addAll(keyFrameCircle);
        }
    }

    public Slider getTimelineSlider(){
        return this.root.timelineTracks.timelineSlider;
    }

    public TimeLineView getRoot(){
        return this.root;
    }

    public String millisToTimecode(long millis){
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
