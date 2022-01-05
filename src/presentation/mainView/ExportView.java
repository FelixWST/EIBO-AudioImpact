package presentation.mainView;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ExportView extends VBox {

    public ExportView(){
        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStyleClass().add("view-element");
        this.getChildren().add(new Label("Test"));
    }
}
