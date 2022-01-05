package presentation.mainView;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TimeLineView extends HBox {
    TimelineTracks timelineTracks;
    TimelineTrackSettings timelineTrackSettings;
    public TimeLineView(){
        timelineTracks = new TimelineTracks();
        timelineTrackSettings = new TimelineTrackSettings();

        timelineTracks.prefWidthProperty().bind(this.widthProperty().multiply(0.8));
        timelineTrackSettings.prefWidthProperty().bind(this.widthProperty().multiply(0.2));

        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStyleClass().add("view-element");
        this.getChildren().addAll(timelineTrackSettings, timelineTracks);
    }
}
