package presentation.mainView;

import business.tracks.AudioTrackType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrackLayerSettings extends VBox {

    Slider volumeSettingSlider;
    Button mute, solo;
    AudioTrackType trackType;
    HBox controlContainer;
    Label trackTypeLabel;

    public TrackLayerSettings(AudioTrackType trackType){
        this.trackType = trackType;
        String colorHex = trackType.getColorHEX();
        int r = Integer.valueOf(colorHex.substring( 1, 3 ),16 );
        int g = Integer.valueOf(colorHex.substring( 3, 5 ),16 );
        int b = Integer.valueOf(colorHex.substring( 5, 7 ),16 );

        controlContainer = new HBox();

        volumeSettingSlider = new Slider(-80, 6, 0);
        volumeSettingSlider.prefWidthProperty().bind(controlContainer.widthProperty().multiply(0.8));
        mute = new Button("M");
        mute.prefWidthProperty().bind(controlContainer.widthProperty().multiply(0.1));
        solo = new Button("S");
        solo.prefWidthProperty().bind(controlContainer.widthProperty().multiply(0.1));

        controlContainer.setAlignment(Pos.CENTER_LEFT);
        this.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(volumeSettingSlider, new Insets(10,10,10,5));
        HBox.setMargin(mute, new Insets(10,10,10,5));
        HBox.setMargin(solo, new Insets(10, 10, 10, 5));

        this.setStyle("-fx-background-color: rgba("+r+","+g+","+b+",0.2)");
        this.getStyleClass().addAll("track-layer-setting");
        controlContainer.getChildren().addAll(volumeSettingSlider, mute, solo);

        trackTypeLabel = new Label(trackType.name());
        trackTypeLabel.getStyleClass().addAll("tracktype-label");
        trackTypeLabel.setStyle("-fx-text-fill: "+colorHex);
        VBox.setMargin(trackTypeLabel, new Insets(10));
        this.getChildren().addAll(trackTypeLabel, controlContainer);
    }
}
