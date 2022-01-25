package presentation.mainView.videoView;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class VideoDropZone extends HBox {
    private Label addLabel;
    private Image addIcon;
    private ImageView iconView;

    public VideoDropZone(){
        this.getStylesheets().add("/presentation/mainView/videoView/videoView.css");
        addIcon = new Image("/data/assets/dropzone.png");
        iconView = new ImageView(addIcon);
        iconView.setFitHeight(200);
        iconView.setPreserveRatio(true);
        addLabel = new Label("Drag Your Video in here to start seeing the impact of Audio!");
        addLabel.setGraphic(iconView);
        addLabel.setContentDisplay(ContentDisplay.TOP);
        this.setId("drag-n-drop-bg");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(addLabel);
    }

    public Label getAddLabel(){
        return this.addLabel;
    }
}
