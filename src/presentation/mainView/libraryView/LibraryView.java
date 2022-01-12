package presentation.mainView.libraryView;

import business.managing.TrackManager;
import business.tracks.MergedTrack;
import com.sun.scenario.effect.Merge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import javax.sound.midi.Track;

public class LibraryView extends VBox {
    ListView<MergedTrack> listView;
    TrackManager trackManager;

    public LibraryView(){
        this.getStylesheets().add("/presentation/mainView/libraryView/libraryView.css");
        this.getStyleClass().add("view-element");
        this.getChildren().add(new Label("Library"));

        listView = new ListView<MergedTrack>();
        listView.getStyleClass().addAll("list-view");

        this.setStyle("-fx-text-fill: black");
        this.getChildren().add(listView);

    }
}
