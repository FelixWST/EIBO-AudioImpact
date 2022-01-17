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
    private HBox cell;
    private VBox trackInfo;

    private Label title;
    private Label genre;
    private Label duration;
    private ImageView cover;
    private Button selectTrack;


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

    public HBox getCell(){return this.cell;}
    public Label getTitle(){return this.title;}
    public Label getGenre(){return this.genre;}
    public Label getDuration(){return this.duration;}
    public ImageView getCover(){return this.cover;}
    public Button getSelectTrack(){return this.selectTrack;}

}
