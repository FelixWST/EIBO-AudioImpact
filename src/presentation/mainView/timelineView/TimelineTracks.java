package presentation.mainView.timelineView;

import business.tracks.AudioTrackType;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.StringConverter;
import presentation.mainView.uicomponents.TrackLayer;

public class TimelineTracks extends VBox {

    TrackLayer tl1, tl2, tl3;
    Slider timelineSlider;

    public TimelineTracks(){
        tl1 = new TrackLayer(AudioTrackType.DEPTH);
        tl2 = new TrackLayer(AudioTrackType.ATMOSPHERE);
        tl3 = new TrackLayer(AudioTrackType.INTENSITY);

        tl1.prefHeightProperty().bind(this.heightProperty().divide(3));
        tl2.prefHeightProperty().bind(this.heightProperty().divide(3));
        tl3.prefHeightProperty().bind(this.heightProperty().divide(3));

        VBox.setMargin(tl1, new Insets(10,10,5,0));
        VBox.setMargin(tl2, new Insets(5,10,5,0));
        VBox.setMargin(tl3, new Insets(5,10,10,0));

        timelineSlider = new Slider();
        timelineSlider.getStyleClass().addAll("timeLineSlider");
        timelineSlider.setShowTickMarks(true);
        timelineSlider.setShowTickLabels(true);
        timelineSlider.setMajorTickUnit(1000);
        timelineSlider.setMinorTickCount(25);
        timelineSlider.setBlockIncrement(1000);
        timelineSlider.setSnapToTicks(true);

        this.getChildren().addAll(timelineSlider, tl1, tl2, tl3);
    }
}
