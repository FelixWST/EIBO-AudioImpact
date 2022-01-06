package business.managing;

import business.tracks.MergedTrack;

import java.util.ArrayList;

public class TrackManager {

    private ArrayList<MergedTrack> trackList;
    private int trackNumber;

    public TrackManager(){
        trackList = new ArrayList<>();
    }

    public TrackManager(ArrayList<MergedTrack> trackList, int trackNumber) {
        this.trackList = trackList;
        this.trackNumber = trackNumber;
    }

    public int getTrackNumber() {
        return this.trackNumber;
    }

    public void addMergedTrack(MergedTrack mergedTrack){
        this.trackList.add(mergedTrack);
    }
}
