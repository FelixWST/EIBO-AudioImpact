package presentation.mainView;

import javafx.scene.layout.Pane;
import presentation.mainView.uicomponents.VideoControl;

public class VideoViewController {

    private VideoView root;
    private VideoControl videoControl;

    public VideoViewController(){
        videoControl = new VideoControl();
        this.root = new VideoView();
        root.getChildren().addAll(videoControl);
    }

    public Pane getRoot(){
        return this.root;
    }
}
