package presentation.mainView.libraryView;

import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import business.tracks.MergedTrack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        root.listView.setItems(content);


        root.listView.setCellFactory(new Callback<ListView<MergedTrack>, ListCell<MergedTrack>>() {
            @Override
            public ListCell<MergedTrack> call(ListView<MergedTrack>  v) {
                return new MergedTrackListCell();
            }
        });

        root.listView.setOnMouseClicked((mouseEvent)->{
            project.setMergedTrackProperty(root.listView.getSelectionModel().getSelectedItem());
            playerManager.changeMergedTrack(root.listView.getSelectionModel().getSelectedItem(), project.getKeyframeManagers());
            if(videoViewController.getMediaPlayer()!=null){
                videoViewController.getMediaPlayer().pause();
            }
        });

        if(project.mergedTrackProperty().get()!=null){
            root.listView.getSelectionModel().select(project.mergedTrackProperty().get());
            root.listView.scrollTo(project.mergedTrackProperty().get());
        }

        project.mergedTrackProperty().addListener(((observableValue, mergedTrack, t1) -> {
            if(t1!=null){
                root.listView.getSelectionModel().select(t1);
                if(!isItemVisibleInListView(root.listView, root.listView.getSelectionModel().getSelectedIndex())){
                    root.listView.scrollTo(t1);
                }
            }
        }));
    }

    public LibraryView getRoot(){
        return this.root;
    }

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
        } catch (Exception ex) {
        }
        return false;
    }

}
