package presentation.mainView.uicomponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class  VideoControl extends VBox {
    Button playButton, jmpPrvKey, jmpNxtKey;
    Label timeLabel;
    HBox controlsBox;

    public VideoControl(){
        controlsBox = new HBox();
        controlsBox.setId("video-control-section");

        playButton = new Button();
        playButton.setId("play-button");
        jmpNxtKey = new Button("");
        jmpNxtKey.setId("skip-fw-button");
        jmpPrvKey = new Button("");
        jmpPrvKey.setId("skip-bw-button");
        timeLabel = new Label("00:00:00:00");


        HBox.setMargin(playButton, new Insets(10));
        HBox.setMargin(jmpNxtKey, new Insets(10));
        HBox.setMargin(jmpPrvKey, new Insets(10));
        HBox.setMargin(timeLabel, new Insets(10));

        controlsBox.getStyleClass().addAll("view-element", "video-control-section");
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.getChildren().addAll(timeLabel, jmpPrvKey,playButton,jmpNxtKey);



        this.getChildren().addAll(controlsBox);
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getJmpPrvKey(){
        return this.jmpPrvKey;
    }

    public Button getJmpNxtKey() {
        return jmpNxtKey;
    }

    public Label getTimeLabel(){
        return this.timeLabel;
    }
}
