package presentation.mainView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class VideoDropZone extends HBox {

    Label addIcon;

    public VideoDropZone(){
        addIcon = new Label("+");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(addIcon);
    }
}
