package presentation.mainView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import presentation.mainView.uicomponents.TrackLayer;

public class TimelineViewController {

    private TimeLineView root;

    public TimelineViewController(){
        root = new TimeLineView();
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println("New height: "+t1.intValue());
                root.timelineTracks.tl1.kf1.setRadius(root.timelineTracks.tl1.getHeight()/10);
            }
        });

    }

    public TimeLineView getRoot(){
        return this.root;
    }
}
