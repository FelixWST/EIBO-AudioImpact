package business.exporting;

import business.editing.KeyframeManager;
import business.tracks.MergedTrack;

import java.util.ArrayList;

public class WaveExporter {

    private ArrayList<KeyframeManager> keyframeManagers;
    private int trackLenghtInMs;
    private int videoLengthInMs;
    private MergedTrack mergedTrack;

    public WaveExporter(ArrayList<KeyframeManager> keyframeManagers, int trackLenghtInMs, int videoLengthInMs, MergedTrack mergedTrack){
        this.keyframeManagers = keyframeManagers;
        this.trackLenghtInMs = trackLenghtInMs;
        this.videoLengthInMs = videoLengthInMs;
        this.mergedTrack = mergedTrack;
    }

    public void export(){
        int i = 1;
        for(KeyframeManager kfm : keyframeManagers) {
                WaveData waveData = new WaveData(kfm, trackLenghtInMs, videoLengthInMs);
                System.out.println(trackLenghtInMs+" "+videoLengthInMs);

                //waveData.readFile("./depth-Ohne Titel Allgemeine Audioformate-1.wav");
                waveData.readFile(mergedTrack.getAudioTrack(kfm.getAudioTrackType()).getWavPath());
                waveData.writeFile("./output"+kfm.getAudioTrackType().name()+".wav");
            i++;
        }
        //Input funktioniert mit Mono 16Bit
    }
}
