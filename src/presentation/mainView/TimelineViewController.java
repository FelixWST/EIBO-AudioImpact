package presentation.mainView;

import business.tracks.AudioTrackType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import presentation.application.Main;

public class TimelineViewController {

    private TimeLineView root;
    private Main application;

    public TimelineViewController(Main application){
        root = new TimeLineView();
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                root.timelineTracks.tl1.kf1.setRadius(root.timelineTracks.tl1.getHeight()/10);
            }
        });

        root.timelineTrackSettings.tls1.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            application.playerManager.setTrackVolume(AudioTrackType.DEPTH, t1.floatValue());
        }));

        root.timelineTrackSettings.tls2.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            application.playerManager.setTrackVolume(AudioTrackType.ATMOSPHERE, t1.floatValue());
        }));

        root.timelineTrackSettings.tls3.volumeSettingSlider.valueProperty().addListener(((observableValue, number, t1) -> {
            application.playerManager.setTrackVolume(AudioTrackType.INTENSITY, t1.floatValue());
        }));

        root.timelineTrackSettings.tls1.mute.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls2.mute.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls3.mute.setOnAction((event)->{

        });


        //SOLO -> DE-SOLO oder solo in Playermanager?
        root.timelineTrackSettings.tls1.solo.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls2.solo.setOnAction((event)->{

        });

        root.timelineTrackSettings.tls3.solo.setOnAction((event)->{

        });


    }

    public TimeLineView getRoot(){
        return this.root;
    }
}
