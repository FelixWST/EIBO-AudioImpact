package presentation.mainView.timelineView;

import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class TimelineTracks extends VBox {
    private Slider timelineSlider;

    public TimelineTracks(){

        timelineSlider = new Slider();
        timelineSlider.getStyleClass().addAll("timeLineSlider");
        timelineSlider.setShowTickMarks(true);
        timelineSlider.setShowTickLabels(true);
        timelineSlider.setMajorTickUnit(1000);
        timelineSlider.setMinorTickCount(25);
        timelineSlider.setBlockIncrement(1000);
        timelineSlider.setSnapToTicks(true);
        timelineSlider.setMinHeight(38);
        timelineSlider.setMaxHeight(38);

        this.getChildren().addAll(timelineSlider);
        System.out.println("TLSlider Height: "+timelineSlider.getHeight());
    }

    public Slider getTimelineSlider(){
        return this.timelineSlider;
    }

    public void resetToDefaultLayout(){
        this.getChildren().clear();
        this.getChildren().add(timelineSlider);
    }
}
