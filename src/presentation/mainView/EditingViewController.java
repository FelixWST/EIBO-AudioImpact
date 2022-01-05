package presentation.mainView;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

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
        TimeLineView timeLineView = new TimeLineView();

        Insets testInsets = new Insets(10);




        File file = new File("src/data/video/videoplayback.mp4");
        MediaViewPane mediaViewPane = new MediaViewPane();
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaViewPane.setMediaView(new MediaView(mediaPlayer));

        mediaViewPane.prefWidthProperty().bind(primaryStage.widthProperty().divide(2));
        libraryView.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        exportView.prefWidthProperty().bind(primaryStage.widthProperty().divide(4));
        timeLineView.prefHeightProperty().bind(primaryStage.heightProperty().divide(2));

        root.setRight(exportView);
        root.setLeft(libraryView);
        root.setCenter(mediaViewPane);
        root.setBottom(timeLineView);

        BorderPane.setMargin(exportView, testInsets);
        BorderPane.setMargin(libraryView, testInsets);
        BorderPane.setMargin(mediaViewPane, testInsets);
        BorderPane.setMargin(timeLineView, testInsets);



    }

    public Pane getRoot(){
        return this.root;
    }
}
