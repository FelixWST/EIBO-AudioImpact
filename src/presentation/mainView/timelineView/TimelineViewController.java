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
    private TimelineTracksController timelineTracksController;
    private TimelineTrackSettingsController timelineTrackSettingsController;

    public TimelineViewController(EditingViewController editingViewController, Project project, PlayerManager playerManager, TrackManager trackManager){
        this.editingViewController = editingViewController;
        this.project = project;
        this.playerManager = playerManager;
        this.trackManager = trackManager;
        this.videoViewController = editingViewController.getVideoViewController();
        this.timelineTracksController = new TimelineTracksController(project);
        this.timelineTrackSettingsController = new TimelineTrackSettingsController(project, playerManager);
        this.root = new TimeLineView();
        timelineTrackSettingsController.getRoot().prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        timelineTracksController.getRoot().prefWidthProperty().bind(root.widthProperty().multiply(0.7));
        root.getChildren().addAll(timelineTrackSettingsController.getRoot(), timelineTracksController.getRoot());


        videoViewController.getMediaPlayer().setOnReady(()->{
            timelineTracksController.getRoot().timelineSlider.setMax(videoViewController.getMediaPlayer().getTotalDuration().toMillis());
            timelineTracksController.repaint();
        });

        timelineTracksController.getRoot().timelineSlider.valueChangingProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){

                Duration dt = Duration.millis(timelineTracksController.getRoot().timelineSlider.getValue());
                videoViewController.getMediaPlayer().seek(dt);
            }
        }));


        timelineTracksController.getRoot().timelineSlider.setLabelFormatter(new StringConverter<Double>() {
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
        return this.timelineTracksController.getRoot().timelineSlider;
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
