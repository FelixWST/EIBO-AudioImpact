package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListViewCell extends ListCell<MergedTrack> {

    private HBox cell;
    private VBox trackInfo;
    private Label title;
    private Label genre;
    private Label duration;
    private ImageView cover;

    public ListViewCell() {
        this.getStylesheets().add("/presentation/mainView/libraryView/libraryView.css");
        this.getStyleClass().addAll("list-cell");

        cell = new HBox();
        cell.setAlignment(Pos.CENTER_LEFT);

        cover = new ImageView();
        trackInfo = new VBox();
        trackInfo.getStyleClass().addAll("track-Info");

        title = new Label();
        title.setId("title-label");
        genre = new Label();
        duration = new Label();

        Insets coverInsets = new Insets(10);
        Insets trackInfoInsets = new Insets(10,10,10,20);

        HBox.setMargin(cover,coverInsets);
        HBox.setMargin(trackInfo, trackInfoInsets);

        trackInfo.getChildren().addAll(title,genre,duration);
        cell.getChildren().addAll(cover, trackInfo);
    }

    public HBox getCell(){return this.cell;}
    public Label getTitle(){return this.title;}
    public Label getGenre(){return this.genre;}
    public Label getDuration(){return this.duration;}
    public ImageView getCover(){return this.cover;}

}
