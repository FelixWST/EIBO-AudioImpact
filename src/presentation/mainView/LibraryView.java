package presentation.mainView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class LibraryView extends VBox {
    ListView<String> listView;

    public LibraryView(){
        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStyleClass().add("view-element");
        this.getChildren().add(new Label("Library"));

        listView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList("Theme 1", "Theme 2", "Theme 3");
        listView.setItems(items);
        listView.setPrefSize(130,250);
        this.getChildren().add(listView);
        //this.setStyle("-fx-background-color: grey");
    }
}
