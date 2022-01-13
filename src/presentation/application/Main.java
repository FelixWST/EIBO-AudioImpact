package presentation.application;

import business.editing.Keyframe;
import business.exporting.WaveExporter;
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
    private EditingViewController editingViewController;
    private VideoFile videoFile;
    private TrackManager trackManager;
    private PlayerManager playerManager;
    private Project project;


    @Override
    public void start(Stage primaryStage){

        trackManager = new TrackManager();
        //trackManager.loadTestTrack();
        trackManager.loadLibrary();

        this.primaryStage = primaryStage;

        project = new Project("Testprojekt", "testproject.prj", "path", trackManager.getMergedTrack(0));
        project.setVideoFile(new VideoFile(new File("src/data/video/videoPlayback.mp4")));

        playerManager = new PlayerManager(project.getMergedTrack(), project.getKeyframeManagers());
        project.getKeyframeManager(AudioTrackType.ATMOSPHERE).addKeyframe(new Keyframe(10,-80));
        project.getKeyframeManager(AudioTrackType.ATMOSPHERE).addKeyframe(new Keyframe(7999,-80));
        project.getKeyframeManager(AudioTrackType.ATMOSPHERE).addKeyframe(new Keyframe(8000,0));
        project.getKeyframeManager(AudioTrackType.ATMOSPHERE).addKeyframe(new Keyframe(15000,6));

        /*project.getKeyframeManager(AudioTrackType.DEPTH).addKeyframe(new Keyframe(4500,3));
        project.getKeyframeManager(AudioTrackType.DEPTH).addKeyframe(new Keyframe(800,6));
        project.getKeyframeManager(AudioTrackType.DEPTH).addKeyframe(new Keyframe(15000,-80));
        System.out.println(project.getKeyframeManager(AudioTrackType.DEPTH).getKeyframes());*/

        project.getKeyframeManager(AudioTrackType.INTENSITY).addKeyframe(new Keyframe(0,-80));
        project.getKeyframeManager(AudioTrackType.INTENSITY).addKeyframe(new Keyframe(8000,0));
        project.getKeyframeManager(AudioTrackType.INTENSITY).addKeyframe(new Keyframe(13400,0));

        editingViewController = new EditingViewController(primaryStage, project, playerManager, trackManager);
        Scene scene = new Scene(editingViewController.getRoot(), 1920, 1080);
        //Wenn keine Properties d
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
