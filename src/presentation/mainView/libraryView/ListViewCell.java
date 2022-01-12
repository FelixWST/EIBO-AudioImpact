package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.List;

public class ListViewCell extends ListCell<MergedTrack> {
    HBox cell;
    VBox trackInfo;

    Label title;
    Label genre;
    Label duration;
    Label cover;


    public ListViewCell() {
       // cover = new Label();
        //cover.setId("mergedTrack-cover");
        trackInfo = new VBox();
        cell = new HBox();
        title = new Label();
        genre = new Label();
        duration = new Label();

        trackInfo.getChildren().addAll(title,genre,duration);
        cell.getChildren().addAll(trackInfo);
    }
}
