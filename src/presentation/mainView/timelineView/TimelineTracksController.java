package presentation.mainView.timelineView;

import business.editing.Keyframe;
import business.editing.KeyframeManager;
import business.managing.Project;
import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import presentation.mainView.uicomponents.TrackLayer;
import java.util.HashMap;

public class TimelineTracksController {

    private TimelineTracks root;
    private HashMap<AudioTrackType, TrackLayer> trackLayers;
    private Project project;
    private double orgSceneX, orgSceneY;

    public TimelineTracksController(Project project){
        this.root = new TimelineTracks();
        this.project = project;
        trackLayers = new HashMap<>();

        for(AudioTrack audioTrack : project.getMergedTrack().getAudioTracks()){
            trackLayers.put(audioTrack.getAudioTrackType(), new TrackLayer(audioTrack.getAudioTrackType()));
            trackLayers.get(audioTrack.getAudioTrackType()).prefHeightProperty().bind(root.heightProperty().divide(project.getMergedTrack().getAudioTracks().size()));
            VBox.setMargin(trackLayers.get(audioTrack.getAudioTrackType()), new Insets(10,10,5,0));
            root.getChildren().add(trackLayers.get(audioTrack.getAudioTrackType()));
        }


        root.widthProperty().addListener(((observableValue, number, t1) -> {
            repaint();
        }));


        //Listener auf Keyframes anmelden (Observable list)
    }

    public TimelineTracks getRoot() {
        return root;
    }

    private void repaint(){
        //TODO: Keyframes must be drawn over shapes to make them fully clickable
        // Dann lines per binding connecten und nicht fest programmieren
        //Line set on mouse clicked auslagern und nicht 4 mal
        //MAP VOLUME TO -80 to +6
        int mapVolumeToPositiveRange = 80;



        long totalDuration = project.getVideoFile().getDuration();
        for(KeyframeManager keyframeManager : project.getKeyframeManagers()){
            TrackLayer selectedTrackLayer = trackLayers.get(keyframeManager.getAudioTrackType());
            selectedTrackLayer.getChildren().clear();
            double pxPerMsHor = selectedTrackLayer.getWidth() / totalDuration;
            Polygon subGraphFill = new Polygon();
            subGraphFill.setFill(keyframeManager.getAudioTrackType().getRGBColor());
            subGraphFill.getPoints().addAll(0.0,selectedTrackLayer.getHeight());
            //selectedTrackLayer.getChildren().addAll(subGraphFill); --> bind all points to Circles
            double pxPerGainVer = selectedTrackLayer.getHeight() / 86;

            Circle lastKeyframeCircle = null;

            if(keyframeManager.getKeyframes().size()==0){
                //If a audiotrack has no Keyframes yet, we can just draw a straight line from beginning to end at the current volume
                Line onlyLine = connect(new Circle(0,selectedTrackLayer.getHeight(),5), new Circle(selectedTrackLayer.getWidth(), selectedTrackLayer.getHeight(),5), keyframeManager.getAudioTrackType());
                onlyLine.setOnMouseClicked((event -> {
                    System.out.println(event.getX()+" | "+ event.getY());

                    int newKfTime = (int) (event.getX()/pxPerMsHor);
                    double newKfValue = ((selectedTrackLayer.getHeight()-event.getY())/pxPerGainVer-mapVolumeToPositiveRange);
                    keyframeManager.addKeyframe(new Keyframe(newKfTime, newKfValue));
                    repaint();
                }));
                selectedTrackLayer.getChildren().add(onlyLine);
                subGraphFill.getPoints().addAll(0.0,selectedTrackLayer.getHeight(), 0.0, selectedTrackLayer.getHeight()- (TrackPlayer.DEFAULT_GAIN *pxPerGainVer+mapVolumeToPositiveRange), selectedTrackLayer.getWidth(), selectedTrackLayer.getHeight()-(TrackPlayer.DEFAULT_GAIN*pxPerGainVer+mapVolumeToPositiveRange), selectedTrackLayer.getWidth(), selectedTrackLayer.getHeight());
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
                }));

                keyframeCircle.setOnMouseDragged((event -> {
                    double deltaX = event.getSceneX() - orgSceneX;
                    double deltaY = event.getSceneY() - orgSceneY;

                    Circle c = (Circle) event.getSource();

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
                    repaint();
                }));

                keyframeCircle.setOnMouseClicked((event -> {
                    if(event.getClickCount()==2){
                        System.out.println("PopUp to make Value Change");
                    }
                    //System.out.println("Ouh i feel clicked :3");
                    //System.out.println(kf.toString());
                    keyframeCircle.setStroke(Color.rgb(255,255,255));
                    keyframeCircle.setStrokeWidth(2);
                }));

               /* keyframeCircle.addEventHandler(MouseEvent.ANY, event -> {
                    if(event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseButton.SECONDARY){
                        keyframeManager.removeKeyframe(kf);
                        repaint();
                    }
                });
*/
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
                    subGraphFill.getPoints().addAll(0.0,keyframeCircle.getCenterY());
                }

                subGraphFill.getPoints().addAll(pxPerMsHor * kf.getTime(),selectedTrackLayer.getHeight()-pxPerGainVer * (kf.getVolume()+mapVolumeToPositiveRange));

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
                subGraphFill.getPoints().addAll(selectedTrackLayer.getWidth(), lastKeyframeCircle.getCenterY(), selectedTrackLayer.getWidth(), selectedTrackLayer.getHeight());
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
}
