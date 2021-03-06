package presentation.mainView;

import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.mainView.exportView.ExportViewController;
import presentation.mainView.libraryView.LibraryViewController;
import presentation.mainView.timelineView.TimelineViewController;
import presentation.mainView.videoView.VideoViewController;

public class EditingViewController {
    private EditingView root;
    private ExportViewController exportViewController;
    private LibraryViewController libraryViewController;
    private VideoViewController videoViewController;
    private TimelineViewController timelineViewController;

    private Project project;
    private PlayerManager playerManager;
    private TrackManager trackManager;

    public EditingViewController(Stage primaryStage, Project project, PlayerManager playerManager, TrackManager trackManager){
        this.project = project;
        this.playerManager = playerManager;
        this.trackManager = trackManager;

        this.root = new EditingView();
        this.exportViewController = new ExportViewController(project);
        this.videoViewController = new VideoViewController(this, project, playerManager, trackManager);
        this.libraryViewController = new LibraryViewController(trackManager, project, playerManager, videoViewController);
        this.timelineViewController = new TimelineViewController(this, project, playerManager, trackManager);

        Insets videoViewInsets = new Insets(10,20,20,20);
        Insets libraryViewInsets = new Insets(10,10,20,20);
        Insets exportViewInsets = new Insets(10,20,20,10);
        Insets timeLineViewInsets = new Insets(20,20,20,20);

        libraryViewController.getRoot().prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        exportViewController.getRoot().prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        timelineViewController.getRoot().prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.5));

        root.setRight(exportViewController.getRoot());
        root.setLeft(libraryViewController.getRoot());
        root.setCenter(videoViewController.getRoot());
        root.setBottom(timelineViewController.getRoot());

        BorderPane.setMargin(exportViewController.getRoot(), exportViewInsets);
        BorderPane.setMargin(libraryViewController.getRoot(), libraryViewInsets);
        BorderPane.setMargin(videoViewController.getRoot(), videoViewInsets);
        BorderPane.setMargin(timelineViewController.getRoot(), timeLineViewInsets);

        primaryStage.setTitle("AudioImpact - Working On: "+project.getProjectTitleProperty().get());
        project.getProjectTitleProperty().addListener(((observableValue, s, t1) -> {
            primaryStage.setTitle("AudioImpact - Working On: "+t1);
        }));
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
