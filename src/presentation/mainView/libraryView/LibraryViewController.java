package presentation.mainView.libraryView;

import business.managing.TrackManager;
import business.tracks.MergedTrack;
import com.sun.scenario.effect.Merge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibraryViewController {

    LibraryView root;
    MergedTrack mergedTrack;
    TrackManager trackManager;

    public LibraryViewController() {
        trackManager = new TrackManager();
        root = new LibraryView();
        //root.listView.getItems().addAll(trackManager.getMergedTrack(0));
        ObservableList<MergedTrack> selected = root.listView.getSelectionModel().getSelectedItems();
        //root.listView.setItems(items);
    }

    public LibraryView getRoot(){
        return this.root;
    }

}
