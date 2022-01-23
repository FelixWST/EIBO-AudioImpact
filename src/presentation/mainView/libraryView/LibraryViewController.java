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
import javafx.util.Callback;

public class LibraryViewController {

    LibraryView root;
    TrackManager trackManager;
    Project project;
    PlayerManager playerManager;

    public LibraryViewController(TrackManager trackManager, Project project, PlayerManager playerManager) {
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
/*
        root.listView.setCellFactory(new Callback<ListView<MergedTrack>, MergedTrackListCell<MergedTrack>>() {
            @Override
            public ListCell<MergedTrack> call(ListView<MergedTrack> mergedTrackListView) {
                return new MergedTrackListCell();
            }
        });

*/

        root.listView.setOnMouseClicked((mouseEvent)->{
            project.setMergedTrackProperty(root.listView.getSelectionModel().getSelectedItem());
            playerManager.changeMergedTrack(root.listView.getSelectionModel().getSelectedItem(), project.getKeyframeManagers());
            //Stop video playback?
        });

        if(project.mergedTrackProperty().get()!=null){
            root.listView.getSelectionModel().select(project.mergedTrackProperty().get());
            root.listView.scrollTo(project.mergedTrackProperty().get());
        }

        project.mergedTrackProperty().addListener(((observableValue, mergedTrack, t1) -> {
            if(t1!=null){
                root.listView.getSelectionModel().select(t1);
                root.listView.scrollTo(t1);
            }
        }));

        root.listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MergedTrack>() {
            @Override
            public void changed(ObservableValue<? extends MergedTrack> observableValue, MergedTrack mergedTrack, MergedTrack t1) {
                if(t1 != null){
                    System.out.println(t1);
                }
            }
        });
    }

    public LibraryView getRoot(){
        return this.root;
    }

}
