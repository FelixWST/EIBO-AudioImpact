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

    public LibraryViewController(TrackManager trackManager) {
        this.trackManager = trackManager;
        root = new LibraryView();

        ObservableList<MergedTrack> content = FXCollections.observableArrayList();
        content.setAll(trackManager.getTrackList());
        root.listView.setItems(content);
        System.out.println(trackManager.getTrackList().toString());
        root.listView.setEditable(true);
    }

    public LibraryView getRoot(){
        return this.root;
    }

}
