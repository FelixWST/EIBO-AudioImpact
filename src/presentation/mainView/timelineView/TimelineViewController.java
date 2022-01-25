package presentation.mainView.timelineView;

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
        this.timelineTrackSettingsController = new TimelineTrackSettingsController(project, playerManager, videoViewController, timelineTracksController);

        this.root = new TimeLineView();
        timelineTrackSettingsController.getRoot().prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        timelineTracksController.getRoot().prefWidthProperty().bind(root.widthProperty().multiply(0.7));
        timelineTrackSettingsController.getRoot().getHybridSlider().prefHeightProperty().bind(timelineTracksController.getRoot().getTimelineSlider().heightProperty());
        root.getChildren().addAll(timelineTrackSettingsController.getRoot(), timelineTracksController.getRoot());
        HBox.setMargin(timelineTrackSettingsController.getRoot(), new Insets(10,0,10,10));
        HBox.setMargin(timelineTracksController.getRoot(), new Insets(10,10,10,0));

        if(project.videoFileProperty().get()!=null){
            initializeVideoControls();
        }

        project.videoFileProperty().addListener(((observableValue, videoFile, t1) -> {
            if(t1!=null){
                initializeVideoControls();
            }
        }));

        timelineTracksController.getRoot().getTimelineSlider().setLabelFormatter(new StringConverter<Double>() {
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

    public void initializeVideoControls(){
        videoViewController.getMediaPlayer().setOnReady(()->{
            timelineTracksController.getRoot().getTimelineSlider().setMax(videoViewController.getMediaPlayer().getTotalDuration().toMillis());
            timelineTracksController.repaint();
        });

        timelineTracksController.getRoot().getTimelineSlider().valueChangingProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(!t1){
                Duration dt = Duration.millis(timelineTracksController.getRoot().getTimelineSlider().getValue());
                new Thread(()->{
                    Platform.runLater(()->{
                        videoViewController.getMediaPlayer().seek(dt);
                    });
                }).start();
            }
        }));
    }

    public Slider getTimelineSlider(){
        return this.timelineTracksController.getRoot().getTimelineSlider();
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
