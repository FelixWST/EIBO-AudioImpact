package presentation.mainView.exportView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExportView extends ScrollPane {

    Label titleLabel, projectSubLabel, fileSubLabel, pathSubLabel;
    TextField projectName, fileName, path;
    Button directory, exportAudio, exportVideo, saveProject;
    HBox directoryContainer;
    VBox mainContainer;

    public ExportView(){
        mainContainer = new VBox();
        titleLabel = new Label("Export");
        titleLabel.getStyleClass().addAll("title-label");
        projectName = new TextField("Project name");
        projectSubLabel = new Label("Project name");
        projectSubLabel.getStyleClass().addAll("label-sub-input");
        fileName = new TextField("Dateiname");
        fileSubLabel = new Label("Filename");
        fileSubLabel.getStyleClass().addAll("label-sub-input");


        path = new TextField("Pfad zu projekt");
        pathSubLabel = new Label("Filepath");
        pathSubLabel.getStyleClass().addAll("label-sub-input");
        directory = new Button("");
        directory.getStyleClass().addAll("directory-btn");

        directoryContainer = new HBox(path, directory);
        path.prefWidthProperty().bind(directoryContainer.widthProperty().subtract(directory.widthProperty()));
        HBox.setMargin(path, new Insets(0,20,0,0));

        exportAudio = new Button("Audio exportieren");
        exportAudio.getStyleClass().addAll("big-btn");
        exportAudio.prefWidthProperty().bind(mainContainer.widthProperty());

        exportVideo = new Button("Fertiges Video exportieren");
        exportVideo.getStyleClass().addAll("big-btn");
        exportVideo.prefWidthProperty().bind(mainContainer.widthProperty());

        saveProject = new Button("Projekt speichern");
        saveProject.getStyleClass().addAll("big-btn");
        saveProject.prefWidthProperty().bind(mainContainer.widthProperty());

        saveProject.setAlignment(Pos.BOTTOM_CENTER);

        Insets titleInset = new Insets(10);
        Insets sublabelInsets = new Insets(2,10,5,10);
        Insets textFieldInsets = new Insets(5,10,0,10);

        VBox.setMargin(titleLabel, titleInset);
        VBox.setMargin(projectName, textFieldInsets);
        VBox.setMargin(projectSubLabel, sublabelInsets);
        VBox.setMargin(fileName, textFieldInsets);
        VBox.setMargin(fileSubLabel, sublabelInsets);
        VBox.setMargin(directoryContainer, textFieldInsets);
        VBox.setMargin(pathSubLabel, sublabelInsets);
        VBox.setMargin(exportAudio, new Insets(10));
        VBox.setMargin(exportVideo, new Insets(10));
        VBox.setMargin(saveProject, new Insets(10));



        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStylesheets().add("/presentation/mainView/exportView/exportView.css");
        mainContainer.getStyleClass().add("view-element");
        mainContainer.getChildren().addAll(titleLabel, projectName,projectSubLabel, fileName, fileSubLabel, directoryContainer, pathSubLabel, exportAudio, exportVideo, saveProject);
        this.fitToWidthProperty().set(true);
        this.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(mainContainer);
    }
}
