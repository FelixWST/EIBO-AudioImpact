package presentation.mainView.timelineView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TimelineTrackSettings extends VBox {
    private Slider totalVolume;
    private ProgressBar volumeProgress;
    private HBox volumeContainer;
    private StackPane hybridSlider;
    private Label volumeLabel;
    private Image headphoneImage;
    private ImageView iv;

    public TimelineTrackSettings(){
        totalVolume = new Slider(-80,0,0);
        totalVolume.setMaxWidth(200.0);
        totalVolume.getStyleClass().addAll("totalVolumeSlider");
        totalVolume.setShowTickMarks(false);
        totalVolume.setShowTickLabels(false);
        totalVolume.setMajorTickUnit(0.1);
        totalVolume.setMinorTickCount(5);

        volumeProgress = new ProgressBar();
        volumeProgress.prefWidthProperty().bind(totalVolume.widthProperty());

        hybridSlider = new StackPane(volumeProgress, totalVolume);
        hybridSlider.setId("hybrid-volume-slider");



        headphoneImage = new Image("/data/assets/headphones.png");
        iv = new ImageView(headphoneImage);
        iv.setFitHeight(15);
        iv.setPreserveRatio(true);

        volumeLabel = new Label();
        volumeLabel.setGraphic(iv);
        volumeContainer = new HBox(volumeLabel, hybridSlider);
        volumeContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(volumeLabel, new Insets(10));


        this.getChildren().addAll(volumeContainer);
    }

    public ProgressBar getVolumeProgress() {
        return volumeProgress;
    }

    public Slider getTotalVolume() {
        return totalVolume;
    }

    public StackPane getHybridSlider() {
        return hybridSlider;
    }

    public void resetToDefaultLayout() {
        this.getChildren().clear();
        this.getChildren().addAll(volumeContainer);
    }
}
