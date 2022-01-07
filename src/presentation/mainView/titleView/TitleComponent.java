package presentation.mainView.titleView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TitleComponent extends HBox {

    public TitleComponent(){
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("view-element");
        this.getChildren().addAll(new Label("Untitled Project"));
    }
}
