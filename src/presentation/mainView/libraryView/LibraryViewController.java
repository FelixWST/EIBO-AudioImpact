package presentation.mainView.libraryView;

import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import business.tracks.MergedTrack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.util.Callback;
import presentation.mainView.videoView.VideoViewController;

public class LibraryViewController {
    private LibraryView root;
    private TrackManager trackManager;
    private Project project;
    private PlayerManager playerManager;
    private int first = 0;
    private int last  = 0;

    public LibraryViewController(TrackManager trackManager, Project project, PlayerManager playerManager, VideoViewController videoViewController) {
        this.trackManager = trackManager;
        this.project = project;
        this.playerManager = playerManager;
        root = new LibraryView();

        ObservableList<MergedTrack> content = FXCollections.observableArrayList();
        content.setAll(trackManager.getTrackList());
        root.getListView().setItems(content);

        root.getListView().setCellFactory(new Callback<ListView<MergedTrack>, ListCell<MergedTrack>>() {
            @Override
            public ListCell<MergedTrack> call(ListView<MergedTrack>  v) {
                return new MergedTrackListCell();
            }
        });

        root.getListView().setOnMouseClicked((mouseEvent)->{
            project.setMergedTrackProperty(root.getListView().getSelectionModel().getSelectedItem());
            playerManager.changeMergedTrack(root.getListView().getSelectionModel().getSelectedItem(), project.getKeyframeManagers());
            if(videoViewController.getMediaPlayer()!=null){
                videoViewController.getMediaPlayer().pause();
            }
        });

        if(project.mergedTrackProperty().get()!=null){
            root.getListView().getSelectionModel().select(project.mergedTrackProperty().get());
            root.getListView().scrollTo(project.mergedTrackProperty().get());
        }

        project.mergedTrackProperty().addListener(((observableValue, mergedTrack, t1) -> {
            if(t1!=null){
                root.getListView().getSelectionModel().select(t1);
                if(!isItemVisibleInListView(root.getListView(), root.getListView().getSelectionModel().getSelectedIndex())){
                    root.getListView().scrollTo(t1);
                }
            }
        }));
    }

    public LibraryView getRoot(){
        return this.root;
    }

    /*Checks if Index is currently visible*/
    public boolean isItemVisibleInListView(ListView<?> t, int index) {
        try {
            ListViewSkin<?> ts = (ListViewSkin<?>) t.getSkin();
            VirtualFlow<?> vf = (VirtualFlow<?>) ts.getChildren().get(0);
            first = vf.getFirstVisibleCell().getIndex();
            last = vf.getLastVisibleCell().getIndex();
            if(index > first && index < last){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

}
