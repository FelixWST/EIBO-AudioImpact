package presentation.mainView.titleView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TitleView extends HBox {

    Label titleLabel;

    public TitleView(){
        titleLabel = new Label();

        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("view-element");
        this.getChildren().addAll(titleLabel);
    }
}
