package presentation.application;

import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import business.managing.VideoFile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.mainView.EditingViewController;

import java.io.*;
import java.util.Properties;

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
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(900);

        trackManager = new TrackManager();

        //If Property: load last Project

        //Else: Create new empty project
        project = new Project("defaultproject", "defaultproject.prj", "path", trackManager.getMergedTrack(0));
        //project.setVideoFile(new VideoFile(new File("src/data/video/videoPlayback.mp4")));
        playerManager = new PlayerManager(project.getMergedTrack(), project.getKeyframeManagers()); //default immer ersten Merged Track wählen?



        editingViewController = new EditingViewController(primaryStage, project, playerManager, trackManager);
        Scene scene = new Scene(editingViewController.getRoot(), 1920, 1080);

        scene.getStylesheets().add("/presentation/application/application.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("AudioImpact");
        primaryStage.show();

        //WaveExporter wf = new WaveExporter(project.getKeyframeManagers(), (int)project.getMergedTrack().getDuration(), 19000, project.getMergedTrack());
        //wf.export();

    }

    public void init(){
        System.setProperty("prism.lcdtext", "false");
    }

    public void stop(){
        saveProperties();
        System.exit(0);
    }

    public void saveProperties(){
        try(OutputStream output = new FileOutputStream("./user.properties")){
            Properties properties = new Properties();

            //Stage Size
            properties.setProperty("stage.lastWidth",""+primaryStage.getWidth());
            properties.setProperty("stage.lastHeight",""+primaryStage.getHeight());

            //Window Position
            properties.setProperty("window.lastX",""+primaryStage.getScene().getWindow().getX());
            properties.setProperty("window.lastY",""+primaryStage.getScene().getWindow().getY());

            //Project related Properties
            project.saveProject(new File("./last.aiprj"));
            properties.setProperty("project.last","./last.aiprj");
            properties.setProperty("project.lastTotalVolume",""+playerManager.totalVolumeProperty().get());

            properties.store(output, null);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadProperties(){
        try {
            InputStream input = new FileInputStream("./user.properties");




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
