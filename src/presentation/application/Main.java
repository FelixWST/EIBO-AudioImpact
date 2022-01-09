package presentation.application;

import business.editing.Keyframe;
import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import business.managing.VideoFile;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.Genre;
import business.tracks.MergedTrack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.mainView.EditingViewController;

import java.io.File;

public class Main extends Application {

    private Stage primaryStage;
    public EditingViewController editingViewController;
    public VideoFile videoFile;
    TrackManager trackManager;
    public PlayerManager playerManager;

    private Project project;


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        trackManager = new TrackManager();
        trackManager.loadTestTrack();

        project = new Project("Testprojekt", "testproject.prj", "path", trackManager.getMergedTrack(0));
        project.setVideoFile(new VideoFile(new File("src/data/video/videoplayback.mp4")));

        playerManager = new PlayerManager(project.getMergedTrack());
        project.getKeyframeManager(AudioTrackType.ATMOSPHERE).addKeyframe(new Keyframe(10,20));
        project.getKeyframeManager(AudioTrackType.ATMOSPHERE).addKeyframe(new Keyframe(1000,20));
        project.getKeyframeManager(AudioTrackType.ATMOSPHERE).addKeyframe(new Keyframe(15000,20));

        editingViewController = new EditingViewController(primaryStage, project, playerManager, trackManager);
        Scene scene = new Scene(editingViewController.getRoot(), 1920, 1080);
        scene.getStylesheets().add("/presentation/application/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void init(){
        System.setProperty("prism.lcdtext", "false");
    }

    public void stop(){
        System.exit(0);
    }
}
