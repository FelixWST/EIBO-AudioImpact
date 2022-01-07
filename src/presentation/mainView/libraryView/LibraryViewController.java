package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import com.sun.scenario.effect.Merge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibraryViewController {

    LibraryView libraryView;
    MergedTrack mergedTrack;

    public LibraryViewController() {
        libraryView = new LibraryView();
        ObservableList<MergedTrack> items = FXCollections.observableArrayList();
        libraryView.listView.setItems(items);
    }

}
