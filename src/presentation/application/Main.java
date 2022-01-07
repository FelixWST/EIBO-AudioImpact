package presentation.application;

import business.managing.PlayerManager;
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


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        AudioTrack testAtmoTrack = new AudioTrack("src/data/testData/exampleTrack/atmosphere.mp3", AudioTrackType.ATMOSPHERE);
        AudioTrack testDepthTrack = new AudioTrack("src/data/testData/exampleTrack/depth.mp3", AudioTrackType.DEPTH);
        AudioTrack testIntensityTrack = new AudioTrack("src/data/testData/exampleTrack/intensity.mp3", AudioTrackType.INTENSITY);

        MergedTrack firstMergedTrack = new MergedTrack("Test", 200, Genre.CINEMATIC);
        firstMergedTrack.addTrack(testAtmoTrack);
        firstMergedTrack.addTrack(testDepthTrack);
        firstMergedTrack.addTrack(testIntensityTrack);

        trackManager = new TrackManager();
        trackManager.addMergedTrack(firstMergedTrack);

        playerManager = new PlayerManager(firstMergedTrack);

        videoFile = new VideoFile(new File("src/data/video/videoplayback.mp4"));



        editingViewController = new EditingViewController(primaryStage, this);
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
