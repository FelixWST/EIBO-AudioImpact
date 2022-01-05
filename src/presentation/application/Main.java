package presentation.application;

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
        Scene scene = new Scene(editingViewController.getRoot(), 500, 500);
        scene.getStylesheets().add("/presentation/application/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void init(){

    }

    public void stop(){
        System.exit(0);
    }
}
