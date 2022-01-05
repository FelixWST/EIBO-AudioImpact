package presentation.mainView.uicomponents;

import business.tracks.AudioTrackType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class TrackLayer extends Pane {
    public Circle kf1, kf2;
    public TrackLayer(AudioTrackType trackType){


        kf1 = new Circle();
        kf1.setRadius(10);
        kf1.setCenterY(20);
        kf1.setCenterX(20);
        kf1.setFill(trackType.getRGBColor());

        kf2 = new Circle();
        kf2.setRadius(10);
        kf2.setFill(trackType.getRGBColor());
        kf2.setCenterX(kf1.getCenterX()+100);
        kf2.setCenterY(kf1.getCenterY()+30);

        Line line = new Line(kf1.getCenterX(), kf1.getCenterY(), kf2.getCenterX(), kf2.getCenterY());
        line.setStroke(trackType.getRGBColor());
        line.setStrokeWidth(2);

        String colorHex = trackType.getColorHEX();
        int r = Integer.valueOf(colorHex.substring( 1, 3 ),16 );
        int g = Integer.valueOf(colorHex.substring( 3, 5 ),16 );
        int b = Integer.valueOf(colorHex.substring( 5, 7 ),16 );

        this.setStyle("-fx-background-color: rgba("+r+","+g+","+b+",0.2)");
        this.getStyleClass().addAll("track-layer");
        this.getChildren().addAll(line, kf1, kf2);
    }
}
