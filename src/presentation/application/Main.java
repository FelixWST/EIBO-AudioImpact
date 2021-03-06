package presentation.application;

import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.mainView.EditingViewController;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class Main extends Application {
    private Stage primaryStage;
    private EditingViewController editingViewController;
    private TrackManager trackManager;
    private PlayerManager playerManager;
    private Project project;
    private double[] windowSize = {1920,1080};
    private double[] windowPos;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        primaryStage.setTitle("AudioImpact");
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(900);

        trackManager = new TrackManager();
        project = new Project("Unnamed Project", "unnamed-export", "", trackManager.getMergedTrack(0), trackManager);
        playerManager = project.getPlayerManager();

        loadProperties();

        editingViewController = new EditingViewController(primaryStage, project, playerManager, trackManager);
        Scene scene = new Scene(editingViewController.getRoot(), windowSize[0], windowSize[1]);

        scene.getStylesheets().add("/presentation/application/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        if(windowPos!=null){
            if(windowPos[0] < Toolkit.getDefaultToolkit().getScreenSize().getWidth()){
                primaryStage.getScene().getWindow().setX(windowPos[0]);
            }
            if(windowPos[1] < Toolkit.getDefaultToolkit().getScreenSize().getWidth()){
                primaryStage.getScene().getWindow().setY(windowPos[1]);
            }
        }
    }

    public void init(){
        System.setProperty("prism.lcdtext", "false");
    }

    public void stop(){
        if(project!=null){
            try {
                project.saveProject(new File("./last.aiprj"));
            } catch (IOException e) {
            }
        }
        saveProperties();
        System.exit(0);
    }

    public void saveProperties(){
        try(OutputStream output = new FileOutputStream("./user.properties")){
            Properties properties = new Properties();

            //Stage Size
            properties.setProperty("stage.lastWidth",""+primaryStage.getScene().getWidth());
            properties.setProperty("stage.lastHeight",""+primaryStage.getScene().getHeight());

            //Window Position
            properties.setProperty("window.lastX",""+primaryStage.getScene().getWindow().getX());
            properties.setProperty("window.lastY",""+primaryStage.getScene().getWindow().getY());

            //Project related Properties
            properties.setProperty("project.lastTotalVolume",""+playerManager.totalVolumeProperty().get());

            properties.store(output, null);

        }catch (IOException e){
        }
    }

    public void loadProperties(){
        try {
            InputStream input = new FileInputStream("./user.properties");
            Properties properties = new Properties();
            properties.load(input);

            if(properties.containsKey("stage.lastWidth") && properties.containsKey("stage.lastHeight")){
                windowSize[0] = Double.parseDouble(properties.getProperty("stage.lastWidth"));
                windowSize[1] = Double.parseDouble(properties.getProperty("stage.lastHeight"));
            }

            if(properties.containsKey("window.lastX") && properties.containsKey("window.lastY")){
                windowPos = new double[2];
                windowPos[0] = Double.parseDouble(properties.getProperty("window.lastX"));
                windowPos[1] = Double.parseDouble(properties.getProperty("window.lastY"));
            }

            if(properties.containsKey("project.lastTotalVolume")){
                playerManager.setTotalVolumeProperty(Double.parseDouble(properties.getProperty("project.lastTotalVolume")));
            }

        } catch (IOException e) {
        }
    }
}
