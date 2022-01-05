package presentation.mainView;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaView;

public class MediaViewPane extends Region {

    private ObjectProperty<MediaView> mediaViewProperty = new SimpleObjectProperty<>();

    public ObjectProperty<MediaView> mediaViewProperty(){
        return this.mediaViewProperty;
    }

    public MediaView getMediaView(){
        return mediaViewProperty.get();
    }

    public void setMediaView(MediaView mediaView){
        this.mediaViewProperty.set(mediaView);
    }

    public MediaViewPane(){
        this(new MediaView());
    }

    @Override
    protected void layoutChildren(){
        MediaView mediaView = mediaViewProperty().get();
        if(mediaView!=null){
            mediaView.setFitWidth(getWidth());
            mediaView.setFitHeight(getHeight());
            layoutInArea(mediaView, 0,0,getWidth(),getHeight(),0, HPos.CENTER, VPos.TOP);
        }
        super.layoutChildren();
    }

    public MediaViewPane(MediaView mediaView){
        mediaViewProperty.addListener(new ChangeListener<MediaView>() {
            @Override
            public void changed(ObservableValue<? extends MediaView> observableValue, MediaView oldMv, MediaView newMv) {
                if(oldMv!=null){
                    getChildren().remove(oldMv);
                }
                if(newMv!=null){
                    getChildren().add(newMv);
                }
            }
        });
        this.mediaViewProperty.set(mediaView);
    }
}
