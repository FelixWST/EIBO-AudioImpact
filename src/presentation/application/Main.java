package presentation.application;

import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        Scene scene = new Scene(new HBox(), 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    //Test Comment
    }

    public void stop(){
        System.exit(0);
    }
}
