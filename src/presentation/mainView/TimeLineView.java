package presentation.mainView;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TimeLineView extends HBox {

    public TimeLineView(){
        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStyleClass().add("view-element");
        this.getChildren().add(new Label("IM A TIMELINE"));
    }
}
