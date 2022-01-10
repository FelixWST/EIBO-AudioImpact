package presentation.mainView.uicomponents;

import business.tracks.AudioTrackType;
import javafx.scene.layout.Pane;

public class TrackLayer extends Pane {
    public TrackLayer(AudioTrackType trackType){
        String colorHex = trackType.getColorHEX();
        int r = Integer.valueOf(colorHex.substring( 1, 3 ),16 );
        int g = Integer.valueOf(colorHex.substring( 3, 5 ),16 );
        int b = Integer.valueOf(colorHex.substring( 5, 7 ),16 );

        this.setStyle("-fx-background-color: rgba("+r+","+g+","+b+",0.2)");
        this.getStyleClass().addAll("track-layer");
    }
}
