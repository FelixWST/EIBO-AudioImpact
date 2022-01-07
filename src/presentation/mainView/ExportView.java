package presentation.mainView;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ExportView extends VBox {

    Label titleLabel;
    TextField projectName, fileName, path;
    Button directory, exportAudio, exportVideo, saveProject;

    public ExportView(){
        titleLabel = new Label("Export");
        projectName = new TextField("Projektname");
        fileName = new TextField("Dateiname");
        path = new TextField("Pfad zu projekt");
        directory = new Button("Dir");
        exportAudio = new Button("Audio exportieren");
        exportVideo = new Button("Fertiges Video exportieren");
        saveProject = new Button("Projekt speichern");

        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStyleClass().add("view-element");
        this.getChildren().addAll(titleLabel, projectName, fileName, path, directory, exportAudio, exportVideo, saveProject);
    }
}
