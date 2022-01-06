package presentation.application;

import business.managing.PlayerManager;
import business.managing.TrackManager;
import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.Genre;
import business.tracks.MergedTrack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import presentation.mainView.EditingViewController;

public class Main extends Application {

    private Stage primaryStage;
    private EditingViewController editingViewController;
    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        editingViewController = new EditingViewController(primaryStage);
        Scene scene = new Scene(editingViewController.getRoot(), 1920, 1080);
        scene.getStylesheets().add("/presentation/application/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        AudioTrack testAtmoTrack = new AudioTrack("src/data/testData/exampleTrack/atmosphere.mp3", AudioTrackType.ATMOSPHERE);
        AudioTrack testDepthTrack = new AudioTrack("src/data/testData/exampleTrack/depth.mp3", AudioTrackType.DEPTH);
        AudioTrack testIntensityTrack = new AudioTrack("src/data/testData/exampleTrack/intensity.mp3", AudioTrackType.INTENSITY);

        MergedTrack firstMergedTrack = new MergedTrack("Test", 200, Genre.CINEMATIC);
        firstMergedTrack.addTrack(testAtmoTrack);
        firstMergedTrack.addTrack(testDepthTrack);
        firstMergedTrack.addTrack(testIntensityTrack);

        TrackManager trackManager = new TrackManager();
        trackManager.addMergedTrack(firstMergedTrack);

        PlayerManager playerManager = new PlayerManager(firstMergedTrack);
        playerManager.startPlaying();



    }

    public void init(){
        System.setProperty("prism.lcdtext", "false");
    }

    public void stop(){
        System.exit(0);
    }
}
