package presentation.mainView.timelineView;

import business.editing.Keyframe;
import business.editing.KeyframeManager;
import business.managing.Project;
import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import presentation.mainView.uicomponents.TrackLayer;

import java.util.HashMap;
import java.util.Optional;

public class TimelineTracksController {

    private TimelineTracks root;
    private HashMap<AudioTrackType, TrackLayer> trackLayers;
    private Project project;
    private double orgSceneX, orgSceneY;

    public TimelineTracksController(Project project){
        this.root = new TimelineTracks();
        this.project = project;
        trackLayers = new HashMap<>();

        initTimelineTracks();

        project.mergedTrackProperty().addListener(((observableValue, mergedTrack, t1) -> {
            initTimelineTracks();
            if(project.videoFileProperty().get()!=null){
            }
        }));
    }

    public TimelineTracks getRoot() {
        return root;
    }

    public void initTimelineTracks(){
        root.resetToDefaultLayout();
        for(AudioTrack audioTrack : project.mergedTrackProperty().get().getAudioTracks()){
            trackLayers.put(audioTrack.getAudioTrackType(), new TrackLayer(audioTrack.getAudioTrackType()));
            trackLayers.get(audioTrack.getAudioTrackType()).prefHeightProperty().bind(root.heightProperty().divide(project.mergedTrackProperty().get().getAudioTracks().size()));
            VBox.setMargin(trackLayers.get(audioTrack.getAudioTrackType()), new Insets(10,10,5,0));
            root.getChildren().add(trackLayers.get(audioTrack.getAudioTrackType()));

            project.getKeyframeManager(audioTrack.getAudioTrackType()).getKeyframes().addListener(new ListChangeListener<Keyframe>() {
                @Override
                public void onChanged(Change<? extends Keyframe> change) {
                    repaint();
                }
            });

            trackLayers.get(audioTrack.getAudioTrackType()).widthProperty().addListener(((observableValue, number, t1) -> {
                if(project.videoFileProperty().get()!=null){
                    repaint();
                }
            }));
        }
    }

    public void repaint(){
        int mapVolumeToPositiveRange = 80;

        long totalDuration = project.videoFileProperty().get().getDuration();

        for(KeyframeManager keyframeManager : project.getKeyframeManagers()){
            TrackLayer selectedTrackLayer = trackLayers.get(keyframeManager.getAudioTrackType());
            selectedTrackLayer.getChildren().clear();
            double pxPerMsHor = selectedTrackLayer.getWidth() / totalDuration;
            double pxPerGainVer = selectedTrackLayer.getHeight() / 86;

            Circle lastKeyframeCircle = null;

            if(keyframeManager.getKeyframes().size()==0){
                //If a audiotrack has no Keyframes yet, we can just draw a straight line from beginning to end at the current volume
                Line onlyLine = connect(new Circle(0,selectedTrackLayer.getHeight()-pxPerGainVer * (TrackPlayer.DEFAULT_GAIN+mapVolumeToPositiveRange),5), new Circle(selectedTrackLayer.getWidth(), selectedTrackLayer.getHeight()-pxPerGainVer * (TrackPlayer.DEFAULT_GAIN+mapVolumeToPositiveRange),5), keyframeManager.getAudioTrackType());
                onlyLine.setOnMouseClicked((event -> {
                    System.out.println(event.getX()+" | "+ event.getY());

                    int newKfTime = (int) (event.getX()/pxPerMsHor);
                    double newKfValue = ((selectedTrackLayer.getHeight()-event.getY())/pxPerGainVer-mapVolumeToPositiveRange);
                    keyframeManager.addKeyframe(new Keyframe(newKfTime, newKfValue));
                    repaint();
                }));
                selectedTrackLayer.getChildren().add(onlyLine);
            }


            for(int i = 0; i<keyframeManager.getKeyframes().size(); i++){
                Keyframe kf = keyframeManager.getKeyframes().get(i);
                Circle keyframeCircle = new Circle();
                keyframeCircle.setRadius(5);
                keyframeCircle.setFill(keyframeManager.getAudioTrackType().getRGBColor());
                keyframeCircle.setCenterX(pxPerMsHor * kf.getTime());
                keyframeCircle.setCenterY(selectedTrackLayer.getHeight()-pxPerGainVer * (kf.getVolume()+mapVolumeToPositiveRange));

                keyframeCircle.setCursor(Cursor.HAND);

                keyframeCircle.setOnMousePressed((event -> {
                    orgSceneX = event.getSceneX();
                    orgSceneY = event.getSceneY();

                    Circle c = (Circle) event.getSource();
                    c.toFront();
                    c.setStroke(Color.rgb(255,255,255));
                    c.setStrokeWidth(2);

                }));

                keyframeCircle.setOnMouseDragged((event -> {
                    double deltaX = event.getSceneX() - orgSceneX;
                    double deltaY = event.getSceneY() - orgSceneY;

                    Circle c = (Circle) event.getSource();
                    c.setStroke(Color.rgb(255,255,255));
                    c.setStrokeWidth(2);

                    double newKfValue = ((selectedTrackLayer.getHeight()-c.getCenterY())/pxPerGainVer-mapVolumeToPositiveRange);
                    kf.setVolume(newKfValue);

                    if((c.getCenterX()+deltaX) > 0 && (c.getCenterX()+deltaX) < selectedTrackLayer.getWidth()){
                        c.setCenterX(c.getCenterX()+deltaX);
                        orgSceneX = event.getSceneX();
                    }
                    if((c.getCenterY()+deltaY) > 0 && (c.getCenterY()+deltaY) < selectedTrackLayer.getHeight()){
                        c.setCenterY(c.getCenterY()+deltaY);
                        orgSceneY = event.getSceneY();
                    }


                }));


                keyframeCircle.setOnMouseReleased((event -> {
                    Circle c = (Circle) event.getSource();
                    int newKfTime = (int) (c.getCenterX()/pxPerMsHor);
                    double newKfValue = ((selectedTrackLayer.getHeight()-c.getCenterY())/pxPerGainVer-mapVolumeToPositiveRange);
                    keyframeManager.removeKeyframe(kf);

                    if(event.getButton() != MouseButton.SECONDARY){
                        keyframeManager.addKeyframe(new Keyframe(newKfTime, newKfValue));
                    }

                    if(event.getClickCount()==2){
                        TextInputDialog textInputDialog = new TextInputDialog(""+kf.getVolume());
                        textInputDialog.setTitle("Change Keyframe Value");
                        textInputDialog.setHeaderText("Enter your new Volume @"+kf.getTime()+" ms");
                        textInputDialog.setContentText("Volume between "+TrackPlayer.MIN_GAIN+" and "+TrackPlayer.MAX_GAIN);

                        Button okButton = (Button) textInputDialog.getDialogPane().lookupButton(ButtonType.OK);
                        TextField inputField = textInputDialog.getEditor();
                        BooleanBinding isValid = Bindings.createBooleanBinding(()->isValidValue(inputField.getText()), inputField.textProperty());
                        okButton.disableProperty().bind(isValid.not());
                        Optional<String> result = textInputDialog.showAndWait();
                        result.ifPresent((value)->{
                            keyframeManager.addKeyframe(new Keyframe(newKfTime, Double.parseDouble(value)));
                        });
                    }

                    repaint();
                }));

                selectedTrackLayer.getChildren().add(keyframeCircle);

                if(lastKeyframeCircle==null){
                    Line firstLine = connect(new Circle(0, keyframeCircle.getCenterY(),5), keyframeCircle, keyframeManager.getAudioTrackType());
                    firstLine.startYProperty().bind(keyframeCircle.centerYProperty());
                    firstLine.setOnMouseClicked((event -> {
                        System.out.println(event.getX()+" | "+ event.getY());

                        int newKfTime = (int) (event.getX()/pxPerMsHor);
                        double newKfValue = ((selectedTrackLayer.getHeight()-event.getY())/pxPerGainVer-mapVolumeToPositiveRange);
                        keyframeManager.addKeyframe(new Keyframe(newKfTime, newKfValue));
                        repaint();
                    }));
                    selectedTrackLayer.getChildren().add(firstLine);
                }

                if(lastKeyframeCircle!=null){
                    Line keyFrameConnect = connect(lastKeyframeCircle, keyframeCircle, keyframeManager.getAudioTrackType());
                    keyFrameConnect.setOnMouseClicked((event -> {
                        System.out.println(event.getX()+" | "+ event.getY());

                        int newKfTime = (int) (event.getX()/pxPerMsHor);
                        double newKfValue = ((selectedTrackLayer.getHeight()-event.getY())/pxPerGainVer-mapVolumeToPositiveRange);
                        keyframeManager.addKeyframe(new Keyframe(newKfTime, newKfValue));
                        repaint();
                    }));
                    selectedTrackLayer.getChildren().add(keyFrameConnect);
                }

                lastKeyframeCircle = keyframeCircle;
            }
            if(lastKeyframeCircle != null){
                Line lineToVoid = connect(lastKeyframeCircle, new Circle(selectedTrackLayer.getWidth(), lastKeyframeCircle.getCenterY(),5), keyframeManager.getAudioTrackType());
                lineToVoid.endYProperty().bind(lastKeyframeCircle.centerYProperty());
                lineToVoid.setOnMouseClicked((event -> {
                    System.out.println(event.getX()+" | "+ event.getY());

                    int newKfTime = (int) (event.getX()/pxPerMsHor);
                    double newKfValue = ((selectedTrackLayer.getHeight()-event.getY())/pxPerGainVer-mapVolumeToPositiveRange);
                    keyframeManager.addKeyframe(new Keyframe(newKfTime, newKfValue));
                    repaint();
                }));
                selectedTrackLayer.getChildren().addAll(lineToVoid);
            }
        }
    }

    private Line connect(Circle c1, Circle c2, AudioTrackType audioTrackType){
        Line line = new Line();
        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStrokeWidth(2);
        line.setStroke(audioTrackType.getRGBColor());

        line.setCursor(Cursor.CROSSHAIR);

        return line;
    }

    private boolean isValidValue(String input){
        //check if is number
        try{
            Double.parseDouble(input);
        }catch (Exception e){
            return false;
        }

        if(Double.parseDouble(input) >= TrackPlayer.MIN_GAIN && Double.parseDouble(input) <= TrackPlayer.MAX_GAIN ){
            return true;
        }
        return false;
    }

    public HashMap<AudioTrackType, TrackLayer> getTrackLayers() {
        return trackLayers;
    }
}
