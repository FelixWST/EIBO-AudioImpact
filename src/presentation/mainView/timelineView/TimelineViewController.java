package presentation.mainView.timelineView;

import business.editing.Keyframe;
import business.editing.KeyframeManager;
import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
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
        this.timelineTrackSettingsController = new TimelineTrackSettingsController(project, playerManager, videoViewController);

        this.root = new TimeLineView();
        timelineTrackSettingsController.getRoot().prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        timelineTracksController.getRoot().prefWidthProperty().bind(root.widthProperty().multiply(0.7));

        root.getChildren().addAll(timelineTrackSettingsController.getRoot(), timelineTracksController.getRoot());
        HBox.setMargin(timelineTrackSettingsController.getRoot(), new Insets(10,0,10,10));
        HBox.setMargin(timelineTracksController.getRoot(), new Insets(10,10,10,0));

        videoViewController.getMediaPlayer().setOnReady(()->{
            timelineTracksController.getRoot().timelineSlider.setMax(videoViewController.getMediaPlayer().getTotalDuration().toMillis());
            timelineTracksController.repaint();
        });

        timelineTracksController.getRoot().timelineSlider.valueChangingProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){
                Duration dt = Duration.millis(timelineTracksController.getRoot().timelineSlider.getValue());
                new Thread(()->{
                    Platform.runLater(()->{
                        System.out.println("StartTime"+videoViewController.getMediaPlayer().getStartTime());
                        System.out.println("EndTime"+videoViewController.getMediaPlayer().getStopTime());
                        System.out.println("seeked duration"+dt);

                        videoViewController.getMediaPlayer().seek(dt);
                    });
                }).start();
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
