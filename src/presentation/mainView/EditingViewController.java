package presentation.mainView;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditingViewController {
    EditingView root;
    ExportView exportView;
    LibraryView libraryView;
    VideoViewController videoViewController;

    public EditingViewController(Stage primaryStage){
        this.root = new EditingView();
        this.exportView = new ExportView();
        this.videoViewController = new VideoViewController();
        this.libraryView = new LibraryView();
        TitleComponent titleComponent = new TitleComponent();
        TimelineViewController timelineViewController = new TimelineViewController();

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
