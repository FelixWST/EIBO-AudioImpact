package presentation.mainView.libraryView;

import business.managing.TrackManager;
import business.tracks.MergedTrack;
import com.sun.scenario.effect.Merge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class LibraryViewController {

    LibraryView root;
    MergedTrack mergedTrack;
    TrackManager trackManager;
    ListViewCell listViewCell;

    public LibraryViewController(TrackManager trackManager) {
        this.trackManager = trackManager;
        root = new LibraryView();

        ObservableList<MergedTrack> content = FXCollections.observableArrayList();
        content.setAll(trackManager.getTrackList());
        root.listView.setItems(content);

        root.listView.setCellFactory(new Callback<ListView<MergedTrack>, ListCell<MergedTrack>>() {
            @Override
            public ListCell<MergedTrack> call(ListView<MergedTrack> mergedTrackListView) {
                return new ListViewCell();
            }
        });

    }

    public LibraryView getRoot(){
        return this.root;
    }

}
