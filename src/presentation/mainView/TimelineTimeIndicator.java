package presentation.mainView;

import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class TimelineTimeIndicator extends HBox {

    Slider timeIndicator;

    public TimelineTimeIndicator(){
        timeIndicator = new Slider(0,1,0);
        timeIndicator.prefWidthProperty().bind(this.widthProperty().multiply(0.7));
        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(timeIndicator);
    }
}
