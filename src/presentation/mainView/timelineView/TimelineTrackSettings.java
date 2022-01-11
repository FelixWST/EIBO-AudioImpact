package presentation.mainView.timelineView;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class TimelineTrackSettings extends VBox {
    Slider totalVolume;

    public TimelineTrackSettings(){
        totalVolume = new Slider(-80,0,0);
        totalVolume.prefWidthProperty().bind(this.widthProperty().multiply(0.8));
        totalVolume.getStyleClass().addAll("totalVolumeSlider");
        totalVolume.setShowTickMarks(true);
        totalVolume.setShowTickLabels(true);
        totalVolume.setMajorTickUnit(0.1);
        totalVolume.setMinorTickCount(5);

        this.getChildren().addAll(totalVolume);
    }
}
