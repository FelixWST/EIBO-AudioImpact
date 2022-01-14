package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
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
            view.title.setText(item.getTitle());
            view.genre.setText(item.getGenre());
            view.duration.setText(String.valueOf(item.getDuration()));
            try{
                System.out.println("Cover Path: "+ item.getCoverPath());
                view.cover.setImage(new Image(new FileInputStream(item.getCoverPath())));
                view.cover.setFitHeight(50);
                view.cover.setFitWidth(50);
          
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }


            this.setGraphic(view.cell);
        } else {
            this.setGraphic(null);
        }

    }
}