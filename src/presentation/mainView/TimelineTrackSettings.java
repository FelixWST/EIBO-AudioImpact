package presentation.mainView;

import business.tracks.AudioTrackType;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class TimelineTrackSettings extends VBox {

    TrackLayerSettings tls1, tls2, tls3;
    public TimelineTrackSettings(){
        tls1 = new TrackLayerSettings(AudioTrackType.DEPTH);
        tls2 = new TrackLayerSettings(AudioTrackType.ATMOSPHERE);
        tls3 = new TrackLayerSettings(AudioTrackType.INTENSITY);

        tls1.prefHeightProperty().bind(this.heightProperty().divide(3));
        tls2.prefHeightProperty().bind(this.heightProperty().divide(3));
        tls3.prefHeightProperty().bind(this.heightProperty().divide(3));

        VBox.setMargin(tls1, new Insets(10,0,5,10));
        VBox.setMargin(tls2, new Insets(5,0,5,10));
        VBox.setMargin(tls3, new Insets(5,0,10,10));

        this.getChildren().addAll(tls1, tls2, tls3);
    }
}