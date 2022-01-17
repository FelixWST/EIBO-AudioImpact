package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.scene.control.Button;
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
    Button selectTrack;


    public ListViewCell() {
        this.getStylesheets().add("/presentation/mainView/libraryView/libraryView.css");
        this.getStyleClass().addAll("list-cell");

        cell = new HBox();

        cover = new ImageView();
        trackInfo = new VBox();
        trackInfo.getStyleClass().addAll("track-Info");

        selectTrack=new Button("select");
        selectTrack.setId("select-Track-Button");



        title = new Label();
        title.setId("title-label");
        genre = new Label();
        genre.setId("genre-label");
        duration = new Label();
        duration.setId("duration-label");

        trackInfo.getChildren().addAll(title,genre,duration);
        cell.getChildren().addAll(cover, trackInfo,selectTrack);
    }
}
