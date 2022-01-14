package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
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
    ImageView cover;


    public ListViewCell() {
        this.getStylesheets().add("/presentation/mainView/libraryView/libraryView.css");

        cover = new ImageView();

        trackInfo = new VBox();
        this.getStyleClass().addAll("list-cell");
        trackInfo.getStyleClass().addAll("track-Info");
        cell = new HBox();
        title = new Label();
        genre = new Label();
        duration = new Label();

        trackInfo.getChildren().addAll(title,genre,duration);
        cell.getChildren().addAll(cover, trackInfo);
    }
}
