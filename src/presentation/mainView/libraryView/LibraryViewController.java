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

        ObservableList<MergedTrack> content = FXCollections.observableArrayList();
        content.setAll(trackManager.getTrackList());
        root.listView.setItems(content);
    }

    public LibraryView getRoot(){
        return this.root;
    }

}
