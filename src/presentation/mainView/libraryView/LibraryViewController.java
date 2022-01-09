package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import com.sun.scenario.effect.Merge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibraryViewController {

    LibraryView root;
    MergedTrack mergedTrack;

    public LibraryViewController() {

        root = new LibraryView();
        ObservableList<MergedTrack> items = FXCollections.observableArrayList();
        root.listView.setItems(items);
    }

    public LibraryView getRoot(){
        return this.root;
    }

}
