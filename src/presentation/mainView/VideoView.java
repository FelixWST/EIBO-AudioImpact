package presentation.mainView;

import javafx.scene.layout.VBox;
import presentation.mainView.uicomponents.VideoControl;

public class VideoView extends VBox {

    public VideoView(VideoPlayer videoPlayer, VideoControl videoControl){
        videoPlayer.prefHeightProperty().bind(this.heightProperty());
        videoControl.prefWidthProperty().bind(videoPlayer.widthProperty());


        this.getChildren().addAll(videoPlayer, videoControl);
    }

}
