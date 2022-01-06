package presentation.mainView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import presentation.mainView.uicomponents.VideoControl;

public class VideoView extends VBox {

    ProgressBar videoProgressBar;
    HBox progressBarContainer;

    public VideoView(VideoPlayer videoPlayer, VideoDropZone videoDropZone, VideoControl videoControl){
        videoPlayer.prefHeightProperty().bind(this.heightProperty());
        videoDropZone.prefHeightProperty().bind(this.heightProperty());

        videoControl.prefWidthProperty().bind(videoPlayer.widthProperty());

        progressBarContainer = new HBox();
        progressBarContainer.setAlignment(Pos.BOTTOM_CENTER);
        videoProgressBar = new ProgressBar(0);
        videoProgressBar.prefWidthProperty().bind(progressBarContainer.widthProperty());
        progressBarContainer.getChildren().addAll(videoProgressBar, new Label());


        this.getChildren().addAll(videoPlayer, progressBarContainer, videoControl);
    }

}
