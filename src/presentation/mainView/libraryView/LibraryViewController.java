package presentation.mainView.libraryView;

import business.managing.PlayerManager;
import business.managing.Project;
import business.managing.TrackManager;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.Genre;
import business.tracks.MergedTrack;
import com.sun.scenario.effect.Merge;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.ArrayList;

public class LibraryViewController {

    LibraryView root;
    MergedTrack mergedTrack;
    TrackManager trackManager;
    ListViewCell listViewCell;
    Project project;
    PlayerManager playerManager;

    public LibraryViewController(TrackManager trackManager, Project project, PlayerManager playerManager) {
        this.trackManager = trackManager;
        this.project = project;
        this.playerManager = playerManager;
        root = new LibraryView();

        ObservableList<MergedTrack> content = FXCollections.observableArrayList();
        content.setAll(trackManager.getTrackList());
        //content.setAll(testList);
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
            if(mouseEvent.getClickCount()==2){
                System.out.println(root.listView.getSelectionModel().getSelectedItem());
                project.setMergedTrack(root.listView.getSelectionModel().getSelectedItem());
                playerManager.changeMergedTrack(root.listView.getSelectionModel().getSelectedItem(), project.getKeyframeManagers());
            }
        });

        root.listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MergedTrack>() {
            @Override
            public void changed(ObservableValue<? extends MergedTrack> observableValue, MergedTrack mergedTrack, MergedTrack t1) {
                if(t1 == null){
                    System.out.println("Empty Cell");
                }else {
                    System.out.println(t1);
                }

            }
        });
    }

    public LibraryView getRoot(){
        return this.root;
    }

}
