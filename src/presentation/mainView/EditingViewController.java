package presentation.mainView;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.application.Main;

public class EditingViewController {
    EditingView root;
    ExportView exportView;
    LibraryView libraryView;
    VideoViewController videoViewController;
    Main application;

    public EditingViewController(Stage primaryStage, Main application){
        this.application = application;
        this.root = new EditingView();
        this.exportView = new ExportView();
        this.videoViewController = new VideoViewController(application);
        this.libraryView = new LibraryView();
        TitleComponent titleComponent = new TitleComponent();
        TimelineViewController timelineViewController = new TimelineViewController(application);

        Insets testInsets = new Insets(10);

        libraryView.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        exportView.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        timelineViewController.getRoot().prefHeightProperty().bind(primaryStage.heightProperty().divide(2));

        //videoViewController.getRoot().getMediaViewPane().prefWidthProperty().bind(primaryStage.widthProperty().divide(2));

        root.setRight(exportView);
        root.setLeft(libraryView);
        root.setCenter(videoViewController.getRoot());
        root.setBottom(timelineViewController.getRoot());
        root.setTop(titleComponent);

        BorderPane.setMargin(exportView, testInsets);
        BorderPane.setMargin(libraryView, testInsets);
        BorderPane.setMargin(titleComponent, testInsets);
        BorderPane.setMargin(videoViewController.getRoot(), testInsets);
        BorderPane.setMargin(timelineViewController.getRoot(), testInsets);



    }

    public Pane getRoot(){
        return this.root;
    }
}
