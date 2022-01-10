package presentation.mainView.timelineView;

import business.managing.PlayerManager;
import business.managing.Project;
import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;


import java.util.HashMap;

public class TimelineTrackSettingsController {

    private TimelineTrackSettings root;
    private HashMap<AudioTrackType, TrackLayerSettings> trackLayers;
    private Project project;
    private PlayerManager playerManager;

    public TimelineTrackSettingsController(Project project, PlayerManager playerManager){
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
                //repaint?
            }));

            deleteAllContext.autoHideProperty().set(true);
            deleteAllContext.setStyle("-fx-background-color: #333333");

            trackLayers.get(audioTrack.getAudioTrackType()).setOnContextMenuRequested((contextMenuEvent -> {
                deleteAllContext.show(trackLayers.get(audioTrack.getAudioTrackType()), contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }));



            trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
                playerManager.setTrackVolume(audioTrack.getAudioTrackType(), t1.floatValue());
                //property von -80 bis +6
                trackLayers.get(audioTrack.getAudioTrackType()).volumeProgress.setProgress((t1.doubleValue()+80)/86);
            }));

            trackLayers.get(audioTrack.getAudioTrackType()).mute.setOnAction((event)->{
                playerManager.toggleMuteOnTrack(audioTrack.getAudioTrackType());
            });

            trackLayers.get(audioTrack.getAudioTrackType()).solo.setOnAction((event)->{

            });

            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).muteProperty().addListener(((observableValue, aBoolean, t1) -> {
                if(t1){
                    trackLayers.get(audioTrack.getAudioTrackType()).mute.setId("mute-active");
                }else{
                    trackLayers.get(audioTrack.getAudioTrackType()).mute.setId("mute-inactive");
                }
            }));

            trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.setValue(playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).volumeProperty().getValue());

            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).volumeProperty().addListener(((observableValue, number, t1) -> {
                Platform.runLater(()->{
                    trackLayers.get(audioTrack.getAudioTrackType()).volumeSettingSlider.setValue(t1.doubleValue());
                });

            }));




        }
    }

    public TimelineTrackSettings getRoot() {
        return root;
    }
}
