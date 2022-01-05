package presentation.mainView;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LibraryView extends VBox {

    public LibraryView(){
        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStyleClass().add("view-element");
        this.getChildren().add(new Label("TestLeft"));
    }
}
