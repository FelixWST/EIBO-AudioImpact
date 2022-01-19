package presentation.application;

import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import business.managing.VideoFile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.mainView.EditingViewController;

import java.io.File;

public class Main extends Application {

    private Stage primaryStage;
    private EditingViewController editingViewController;
    private VideoFile videoFile;
    private TrackManager trackManager;
    private PlayerManager playerManager;
    private Project project;


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        trackManager = new TrackManager();
        trackManager.scanFiles();

        //If Property: load last Project

        //Else: Create new empty project
        project = new Project("defaultproject", "defaultproject.prj", "path", trackManager.getMergedTrack(0));
        project.setVideoFile(new VideoFile(new File("src/data/video/videoPlayback.mp4")));
        playerManager = new PlayerManager(project.getMergedTrack(), project.getKeyframeManagers());



        editingViewController = new EditingViewController(primaryStage, project, playerManager, trackManager);
        Scene scene = new Scene(editingViewController.getRoot(), 1920, 1080);

        scene.getStylesheets().add("/presentation/application/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        //WaveExporter wf = new WaveExporter(project.getKeyframeManagers(), (int)project.getMergedTrack().getDuration(), 19000, project.getMergedTrack());
        //wf.export();

    }

    public void init(){
        System.setProperty("prism.lcdtext", "false");
    }

    public void stop(){
        System.exit(0);
    }
}
