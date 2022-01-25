package presentation.mainView.timelineView;

import business.editing.Keyframe;
import business.managing.PlayerManager;
import business.managing.Project;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
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
    private VideoViewController videoViewController;
    private TimelineTracksController timelineTracksController;
    private TimeLineView mainRoot;

    public TimelineTrackSettingsController(Project project, PlayerManager playerManager, VideoViewController videoViewController, TimelineTracksController timelineTracksController){
        this.root = new TimelineTrackSettings();
        this.project = project;
        this.playerManager = playerManager;
        this.videoViewController = videoViewController;
        this.timelineTracksController = timelineTracksController;

        Platform.runLater(()->{
            initializeTrackSettings();
        });

        //When VideoFile Changes -> Duration of Timeline Changes -> Reinitialize
        project.videoFileProperty().addListener(((observableValue, videoFile, t1) -> {
            Platform.runLater(()->{
                initializeTrackSettings();
            });
        }));

        //When MergedTrack Changes -> KeyframeManagers change -> Reinitialize
        project.mergedTrackProperty().addListener(((observableValue, mergedTrack, t1) -> {
            Platform.runLater(()->{
                initializeTrackSettings();
            });
        }));

        root.getTotalVolume().setValue(playerManager.totalVolumeProperty().getValue());
        root.getVolumeProgress().setProgress((root.getTotalVolume().getValue()+80)/80);
        root.getTotalVolume().valueProperty().addListener(((observableValue, aBoolean, t1) -> {
            playerManager.setTotalVolumeProperty(t1.doubleValue());
            root.getVolumeProgress().setProgress((t1.doubleValue()+80)/80);
        }));
    }

    public TimelineTrackSettings getRoot() {
        return root;
    }

    public void initializeTrackSettings(){
        root.resetToDefaultLayout();
        trackLayers = new HashMap<>();

        //Create TrackLayerSetting For each AudioTrack of MergedTrack
        for(AudioTrack audioTrack : project.mergedTrackProperty().get().getAudioTracks()){
            trackLayers.put(audioTrack.getAudioTrackType(), new TrackLayerSettings(audioTrack.getAudioTrackType()));
            String id = "";
            switch (audioTrack.getAudioTrackType()){
                case DEPTH -> id = "track-settings-volume-depth";
                case INTENSITY -> id = "track-settings-volume-intensity";
                case ATMOSPHERE -> id = "track-settings-volume-atmosphere";
            }
            trackLayers.get(audioTrack.getAudioTrackType()).getVolumeProgress().setId(id);
            trackLayers.get(audioTrack.getAudioTrackType()).getVolumeSettingSlider().setId(id);

            trackLayers.get(audioTrack.getAudioTrackType()).prefHeightProperty().bind((root.heightProperty().divide(project.mergedTrackProperty().get().getAudioTracks().size())));
            VBox.setMargin(trackLayers.get(audioTrack.getAudioTrackType()), new Insets(10,0,5,10));
            root.getChildren().add(trackLayers.get(audioTrack.getAudioTrackType()));

            //Contextmenu for deleting all Keyframes of Track
            MenuItem deleteAll = new MenuItem("Clear all Keyframes");
            ContextMenu deleteAllContext = new ContextMenu(deleteAll);
            deleteAll.setOnAction((actionEvent -> {
                project.getKeyframeManager(audioTrack.getAudioTrackType()).clearAllKeyframes();
            }));
            deleteAllContext.autoHideProperty().set(true);

            trackLayers.get(audioTrack.getAudioTrackType()).setOnContextMenuRequested((contextMenuEvent -> {
                deleteAllContext.show(trackLayers.get(audioTrack.getAudioTrackType()), contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            }));


            //Moving Slider while Playing creates Keyframes in an Interval
            AtomicLong lastMilliTime = new AtomicLong(System.currentTimeMillis());
            trackLayers.get(audioTrack.getAudioTrackType()).getVolumeSettingSlider().valueProperty().addListener(((observableValue, number, t1) -> {

                if (trackLayers.get(audioTrack.getAudioTrackType()).getVolumeSettingSlider().valueChangingProperty().get()) {
                    Platform.runLater(() -> {
                        playerManager.setTrackVolume(audioTrack.getAudioTrackType(), t1.floatValue());
                        if (videoViewController.getMediaPlayer().statusProperty().get() == MediaPlayer.Status.PLAYING) {
                            //Check Interval after last Keyframe
                            if (System.currentTimeMillis() - lastMilliTime.get() > 500) {
                                project.getKeyframeManager(audioTrack.getAudioTrackType()).addKeyframe(new Keyframe(playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).getPosition(), t1.doubleValue()));
                                lastMilliTime.set(System.currentTimeMillis());
                            }
                        } else {
                            project.getKeyframeManager(audioTrack.getAudioTrackType()).addKeyframe(new Keyframe(playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).getPosition(), t1.doubleValue()));
                        }

                    });
                }
                //Set progressbar of hybrid slider (mapped to -80 bis +6)
                trackLayers.get(audioTrack.getAudioTrackType()).getVolumeProgress().setProgress((t1.doubleValue() + 80) / 86);
            }));

            //Slider is disabled as long as no audioplayer or Video is loaded
            if(playerManager == null || videoViewController.getMediaPlayer() == null){
                trackLayers.get(audioTrack.getAudioTrackType()).getVolumeSettingSlider().setDisable(true);
            }

            trackLayers.get(audioTrack.getAudioTrackType()).getMute().setOnAction((event)->{
                playerManager.toggleMuteOnTrack(audioTrack.getAudioTrackType());
            });

            trackLayers.get(audioTrack.getAudioTrackType()).getSolo().setOnAction((event)->{
                playerManager.toggleSoloOnTrack(audioTrack.getAudioTrackType());
            });

            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).muteProperty().addListener(((observableValue, aBoolean, t1) -> {
                if(t1){
                    trackLayers.get(audioTrack.getAudioTrackType()).getMute().setId("mute-active");
                }else{
                    trackLayers.get(audioTrack.getAudioTrackType()).getMute().setId("mute-inactive");
                }
            }));

            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).soloProperty().addListener(((observableValue, aBoolean, t1) -> {
                if(t1){
                    trackLayers.get(audioTrack.getAudioTrackType()).getSolo().setId("solo-active");
                }else{
                    trackLayers.get(audioTrack.getAudioTrackType()).getSolo().setId("solo-inactive");
                }
            }));

            trackLayers.get(audioTrack.getAudioTrackType()).getVolumeSettingSlider().setValue(playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).volumeProperty().getValue());

            //Only update Slider with no volume, if it is not dragged atm
            playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).volumeProperty().addListener(((observableValue, number, t1) -> {
                if(!trackLayers.get(audioTrack.getAudioTrackType()).getVolumeSettingSlider().valueChangingProperty().get()){
                    Platform.runLater(()->{
                        trackLayers.get(audioTrack.getAudioTrackType()).getVolumeSettingSlider().setValue(t1.doubleValue());
                    });
                }
            }));

            project.getKeyframeManager(audioTrack.getAudioTrackType()).getKeyframes().addListener(new ListChangeListener<Keyframe>() {
                @Override
                public void onChanged(Change<? extends Keyframe> change) {
                    playerManager.getTrackPlayer(audioTrack.getAudioTrackType()).updateVolumeAtCurrentTime();
                }
            });
        }
    }
}
