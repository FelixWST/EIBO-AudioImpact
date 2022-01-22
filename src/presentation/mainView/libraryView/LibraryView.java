package presentation.mainView.libraryView;

import business.managing.TrackManager;
import business.tracks.MergedTrack;
import com.sun.scenario.effect.Merge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import javax.sound.midi.Track;

public class LibraryView extends VBox {
    ListView<MergedTrack> listView;
    TrackManager trackManager;
    Label titleLabel;

    public LibraryView(){
        this.getStylesheets().add("/presentation/mainView/libraryView/libraryView.css");
        this.getStyleClass().add("view-element");
        titleLabel = new Label("Library");
        titleLabel.getStyleClass().add("title-label");

        listView = new ListView<MergedTrack>();
        listView.getStyleClass().addAll("list-view");

        Insets titleInset = new Insets(10);

        VBox.setMargin(titleLabel, titleInset);

        this.getChildren().addAll(titleLabel, listView);

    }
}
