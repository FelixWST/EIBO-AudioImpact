package presentation.mainView.timelineView;

import business.editing.Keyframe;
import business.managing.PlayerManager;
import business.managing.Project;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import presentation.mainView.videoView.VideoViewController;


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TimelineTrackSettingsController {

    private TimelineTrackSettings root;
    private HashMap<AudioTrackType, TrackLayerSettings> trackLayers;
    private Project project;
    private PlayerManager playerManager;

    public TimelineTrackSettingsController(Project project, PlayerManager playerManager, VideoViewController videoViewController){
        this.root = new TimelineTrackSettings();
        this.project = project;
        this.playerManager = playerManager;
        trackLayers = new HashMap<>();

        for(AudioTrack audioTrack : project.getMergedTrack().getAudioTracks()){
            trackLayers.put(audioTrack.getAudioTrackType(), new TrackLayerSettings(audioTrack.getAudioTrackType()));
            String id = "";
            switch (audioTrack.getAudioTrackType()){
                case DEPTH -> id = "track-settings-volume-depth";
                case INTENSITY -> id = "track-settings-volume-intensity";
                case ATMOSPHERE -> id = "track-settings-volume-atmosphere";
            }
            trackLayers.get(audioTrack.getAudioTrackType()).volumeProgress.setId(id);
            trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.setId(id);

            trackLayers.get(audioTrack.getAudioTrackType()).prefHeightProperty().bind(root.heightProperty().divide(project.getMergedTrack().getAudioTracks().size()));
            VBox.setMargin(trackLayers.get(audioTrack.getAudioTrackType()), new Insets(10,0,5,10));
            root.getChildren().add(trackLayers.get(audioTrack.getAudioTrackType()));


            MenuItem deleteAll = new MenuItem("Clear all Keyframes");
            ContextMenu deleteAllContext = new ContextMenu(deleteAll);
            deleteAll.setOnAction((actionEvent -> {
                project.getKeyframeManager(audioTrack.getAudioTrackType()).clearAllKeyframes();
            }));

            deleteAllContext.autoHideProperty().set(true);
            deleteAllContext.setStyle("-fx-background-color: #333333");

            trackLayers.get(audioTrack.getAudioTrackType()).setOnContextMenuRequested((contextMenuEvent -> {
                deleteAllContext.show(trackLayers.get(audioTrack.getAudioTrackType()), contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }));


            AtomicLong lastMilliTime = new AtomicLong(System.currentTimeMillis());
            trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
                playerManager.setTrackVolume(audioTrack.getAudioTrackType(), t1.floatValue());
                if(trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.valueChangingProperty().get()){
                    Platform.runLater(()->{
                        if(videoViewController.getMediaPlayer().statusProperty().get() == MediaPlayer.Status.PLAYING){
                            if(System.currentTimeMillis()- lastMilliTime.get() > 500){
                                project.getKeyframeManager(audioTrack.getAudioTrackType()).addKeyframe(new Keyframe(playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).getPosition(), t1.doubleValue()));
                                lastMilliTime.set(System.currentTimeMillis());
                            }
                        }else{
                            project.getKeyframeManager(audioTrack.getAudioTrackType()).addKeyframe(new Keyframe(playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).getPosition(), t1.doubleValue()));
                        }

                    });
                }
                //property von -80 bis +6
                trackLayers.get(audioTrack.getAudioTrackType()).volumeProgress.setProgress((t1.doubleValue()+80)/86);
            }));

            trackLayers.get(audioTrack.getAudioTrackType()).mute.setOnAction((event)->{
                playerManager.toggleMuteOnTrack(audioTrack.getAudioTrackType());
            });

            trackLayers.get(audioTrack.getAudioTrackType()).solo.setOnAction((event)->{
                playerManager.toggleSoloOnTrack(audioTrack.getAudioTrackType());
            });

            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).muteProperty().addListener(((observableValue, aBoolean, t1) -> {
                if(t1){
                    trackLayers.get(audioTrack.getAudioTrackType()).mute.setId("mute-active");
                }else{
                    trackLayers.get(audioTrack.getAudioTrackType()).mute.setId("mute-inactive");
                }
            }));

            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).soloProperty().addListener(((observableValue, aBoolean, t1) -> {
                if(t1){
                    trackLayers.get(audioTrack.getAudioTrackType()).solo.setId("solo-active");
                }else{
                    trackLayers.get(audioTrack.getAudioTrackType()).solo.setId("solo-inactive");
                }
            }));

            trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.setValue(playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).volumeProperty().getValue());

            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).volumeProperty().addListener(((observableValue, number, t1) -> {

            if(!trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.valueChangingProperty().get()){
                Platform.runLater(()->{
                    trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.setValue(t1.doubleValue());
                });
            }
            }));
        }

        root.totalVolume.valueProperty().addListener(((observableValue, aBoolean, t1) -> {
            playerManager.totalVolumeProperty().set(t1.doubleValue());
        }));
    }

    public TimelineTrackSettings getRoot() {
        return root;
    }
}
