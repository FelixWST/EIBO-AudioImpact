package presentation.mainView;

import javafx.scene.layout.Pane;

public class EditingViewController {
    EditingView root;
    ExportView exportView;
    VideoViewController videoViewController;

    public EditingViewController(){
        this.root = new EditingView();
        this.exportView = new ExportView();
        this.videoViewController = new VideoViewController();
        root.setRight(exportView);
        root.setCenter(videoViewController.getRoot());

    }

    public Pane getRoot(){
        return this.root;
    }
}
