package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

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

            this.setGraphic(view.trackInfo);
        } else {
            this.setGraphic(null);
        }

    }
}