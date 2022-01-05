package presentation.mainView;

import presentation.mainView.uicomponents.VideoControl;

public class VideoViewController {

    private VideoView root;
    private VideoPlayer videoPlayer;
    private VideoControl videoControl;

    public VideoViewController(){
        this.videoControl = new VideoControl();
        this.videoPlayer = new VideoPlayer();
        this.root = new VideoView(videoPlayer, videoControl);
    }

    public VideoView getRoot(){
        return this.root;
    }
}
