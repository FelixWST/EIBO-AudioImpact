package presentation.mainView;

import javafx.scene.layout.Pane;

public class EditingViewController {
    EditingView root;
    ExportView exportView;
    VideoView videoView;

    public EditingViewController(){
        this.root = new EditingView();
        this.exportView = new ExportView();
        this.videoView = new VideoView();
        root.setRight(exportView);
        root.setCenter(videoView);

    }

    public Pane getRoot(){
        return this.root;
    }
}
