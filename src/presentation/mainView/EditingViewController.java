package presentation.mainView;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.application.Main;
import presentation.mainView.exportView.ExportView;
import presentation.mainView.libraryView.LibraryView;
import presentation.mainView.timelineView.TimelineViewController;
import presentation.mainView.titleView.TitleComponent;
import presentation.mainView.videoView.VideoViewController;

public class EditingViewController {
    EditingView root;
    ExportView exportView;
    LibraryView libraryView;
    VideoViewController videoViewController;
    TimelineViewController timelineViewController;
    Main application;

    public EditingViewController(Stage primaryStage, Main application){
        this.application = application;
        this.root = new EditingView();
        this.exportView = new ExportView();
        this.videoViewController = new VideoViewController(application);
        this.libraryView = new LibraryView();
        TitleComponent titleComponent = new TitleComponent();
        timelineViewController = new TimelineViewController(application, videoViewController);

        Insets titleInsets = new Insets(10,20,0,20);
        Insets videoViewInsets = new Insets(10,20,20,20);
        Insets libraryViewInsets = new Insets(10,10,20,20);
        Insets exportViewInsets = new Insets(10,20,20,10);
        Insets timeLineViewInsets = new Insets(20,20,20,20);

        libraryView.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        exportView.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        timelineViewController.getTimeLineView().prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.5));

        //videoViewController.getRoot().getMediaViewPane().prefWidthProperty().bind(primaryStage.widthProperty().divide(2));

        root.setRight(exportView);
        root.setLeft(libraryView);
        root.setCenter(videoViewController.getRoot());
        root.setBottom(timelineViewController.getTimeLineView());
        root.setTop(titleComponent);

        BorderPane.setMargin(exportView, exportViewInsets);
        BorderPane.setMargin(libraryView, libraryViewInsets);
        BorderPane.setMargin(titleComponent, titleInsets);
        BorderPane.setMargin(videoViewController.getRoot(), videoViewInsets);
        BorderPane.setMargin(timelineViewController.getTimeLineView(), timeLineViewInsets);



    }

    public VideoViewController getVideoViewController(){
        return this.videoViewController;
    }

    public TimelineViewController getTimelineViewController(){
        return this.timelineViewController;
    }

    public Pane getRoot(){
        return this.root;
    }
}
