package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MergedTrackListCell extends ListCell<MergedTrack> {

    private ListViewCell view;

    public MergedTrackListCell() {
        view = new ListViewCell();
        this.setGraphic(view);
    }

    @Override
    protected void updateItem(MergedTrack item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            view.getTitle().setText(item.getTitle());
            view.getGenre().setText(item.getGenre());
            view.getDuration().setText(String.valueOf(item.getDuration())+"sec");
            try{
                System.out.println("Cover Path: "+ item.getCoverPath());
                view.getCover().setImage(new Image(new FileInputStream(item.getCoverPath())));
                view.getCover().setFitHeight(50);
                view.getCover().setFitWidth(50);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            this.setGraphic(view.getCell());
        } else {
            this.setGraphic(null);
        }
    }
}