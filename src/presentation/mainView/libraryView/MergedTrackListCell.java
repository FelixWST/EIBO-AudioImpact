package presentation.mainView.libraryView;

import business.tracks.MergedTrack;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
            view.getGenre().setText("Genre: "+item.getGenre());
            view.getDuration().setText("Duration: "+secondsToFormatString(item.getDuration()));
            try{
                view.getCover().setImage(new Image(new FileInputStream(item.getCoverPath())));
                view.getCover().setFitHeight(70);
                view.getCover().setFitWidth(70);
            }catch(FileNotFoundException e){
            }
            this.setGraphic(view.getCell());
        } else {
            this.setGraphic(null);
        }
    }

    private String secondsToFormatString(long duration){
        long minutes = duration / 60;
        long seconds = duration % 60;
        String outputString = "";
        if(minutes<10){
            outputString = "0"+minutes+":";
        }else{
            outputString = minutes+":";
        }

        if(seconds<10){
            outputString+="0"+seconds;
        }else{
            outputString+=seconds;
        }
        return outputString;
    }
}