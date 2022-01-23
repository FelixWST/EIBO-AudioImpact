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

    private Label titleLabel, projectSubLabel, fileSubLabel, pathSubLabel;
    private TextField projectName, fileName, path;
    private Button directory, exportAudio, saveProject, openProject;
    private HBox directoryContainer;
    private VBox mainContainer;

    public ExportView(){
        mainContainer = new VBox();
        titleLabel = new Label("Export");
        titleLabel.getStyleClass().addAll("title-label");
        projectName = new TextField();
        projectName.setFocusTraversable(false);
        projectSubLabel = new Label("Project Title");
        projectSubLabel.getStyleClass().addAll("label-sub-input");
        fileName = new TextField();
        fileName.setFocusTraversable(false);
        fileSubLabel = new Label("Filename");
        fileSubLabel.getStyleClass().addAll("label-sub-input");


        path = new TextField();
        path.setEditable(false);
        path.setFocusTraversable(false);
        path.setId("no-focus");
        pathSubLabel = new Label("Export Directory");
        pathSubLabel.getStyleClass().addAll("label-sub-input");
        directory = new Button("");
        directory.getStyleClass().addAll("directory-btn");
        directory.setPrefWidth(50);

        directoryContainer = new HBox(path, directory);
        path.prefWidthProperty().bind(directoryContainer.widthProperty().subtract(directory.widthProperty()));
        HBox.setMargin(path, new Insets(0,20,0,0));

        exportAudio = new Button("Export Audiotracks");
        exportAudio.getStyleClass().addAll("big-btn");
        exportAudio.prefWidthProperty().bind(mainContainer.widthProperty());

        saveProject = new Button("Save Project");
        saveProject.getStyleClass().addAll("big-btn");
        saveProject.prefWidthProperty().bind(mainContainer.widthProperty());

        openProject = new Button("Open Project");
        openProject.getStyleClass().addAll("big-btn");
        openProject.prefWidthProperty().bind(mainContainer.widthProperty());

        openProject.setAlignment(Pos.BOTTOM_CENTER);

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
        VBox.setMargin(saveProject, new Insets(10));
        VBox.setMargin(openProject, new Insets(10));

        mainContainer.prefHeightProperty().bind(this.heightProperty());



        this.getStylesheets().add("/presentation/mainView/editingView.css");
        this.getStylesheets().add("/presentation/mainView/exportView/exportView.css");
        mainContainer.getStyleClass().add("view-element");
        mainContainer.getChildren().addAll(titleLabel, projectName,projectSubLabel, fileName, fileSubLabel, directoryContainer, pathSubLabel, exportAudio, saveProject, openProject);
        this.fitToWidthProperty().set(true);
        this.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(mainContainer);
    }

    public TextField getProjectNameTextField(){
        return this.projectName;
    }

    public TextField getFileNameTextField(){
        return this.fileName;
    }

    public Button getOpenProject() {
        return openProject;
    }

    public Button getSaveProject() {
        return saveProject;
    }

    public Button getExportAudio() {
        return exportAudio;
    }

    public Button getDirectory() {
        return directory;
    }

    public TextField getPath(){
        return this.path;
    }
}
