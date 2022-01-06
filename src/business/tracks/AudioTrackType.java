package business.tracks;

import javafx.scene.paint.Color;

public enum AudioTrackType {
    INTENSITY(Color.rgb(251,60,59), "#FB3C3B"),
    DEPTH(Color.rgb(252,196,41),"#FCC429"),
    ATMOSPHERE(Color.rgb(85,254,248),"#55FEF8");

    private Color colorRGB;
    private String colorHEX;

    AudioTrackType(Color color, String colorHEX){
        this.colorRGB = color;
        this.colorHEX = colorHEX;
    }

    public Color getRGBColor(){
        return this.colorRGB;
    }

    public String getColorHEX(){
        return this.colorHEX;
    }


}
