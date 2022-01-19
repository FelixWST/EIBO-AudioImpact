package presentation.mainView.videoView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import presentation.mainView.uicomponents.VideoControl;

public class VideoView extends VBox {

    ProgressBar videoProgressBar;
    StackPane progressBarContainer;
    private VideoPlayer videoPlayer;
    private VideoControl videoControl;
    private VideoDropZone videoDropZone;

    public VideoView(VideoPlayer videoPlayer, VideoDropZone videoDropZone, VideoControl videoControl){
        this.videoPlayer = videoPlayer;
        this.videoControl = videoControl;
        this.videoDropZone = videoDropZone;

        videoPlayer.prefHeightProperty().bind(this.heightProperty());
        videoDropZone.prefHeightProperty().bind(this.heightProperty());

        videoControl.prefWidthProperty().bind(videoPlayer.widthProperty());

        progressBarContainer = new StackPane();
        progressBarContainer.setAlignment(Pos.BOTTOM_CENTER);
        videoProgressBar = new ProgressBar(0);
        videoProgressBar.prefWidthProperty().bind(progressBarContainer.widthProperty());

        progressBarContainer.getChildren().addAll(videoProgressBar, new Label());
    }

    public void toDropZoneLayout(){
        this.getChildren().clear();
        this.getChildren().addAll(videoDropZone);
    }

    public void toVideoViewLayout(){
        this.getChildren().clear();
        this.getChildren().addAll(videoPlayer, progressBarContainer, videoControl);
    }

}
