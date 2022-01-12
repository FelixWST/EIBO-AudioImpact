package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class ListViewCell extends ListCell<MergedTrack> {
    HBox trackInfo;
    Label title;
    Label genre;
    Label duration;

    public ListViewCell() {

        HBox root;
        trackInfo = new HBox();
        title = new Label();
        genre = new Label();
        duration = new Label();
        trackInfo.getChildren().addAll(title, genre, duration);
    }
}
