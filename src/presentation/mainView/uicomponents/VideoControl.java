package presentation.mainView.uicomponents;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class VideoControl extends HBox {
    Button playButton, jmpPrvKey, jmpNxtKey;
    Label timeLabel;

    public VideoControl(){
        playButton = new Button("Play");
        jmpNxtKey = new Button("Next");
        jmpPrvKey = new Button("Prev");
        timeLabel = new Label("00:00:01");
        this.getStyleClass().addAll("view-element", "video-control-section");
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(timeLabel, jmpPrvKey,playButton,jmpNxtKey);
    }
}
