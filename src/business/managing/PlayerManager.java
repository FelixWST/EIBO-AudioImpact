package business.managing;

import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.MergedTrack;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    MergedTrack mergedTrack;
    HashMap<AudioTrackType, TrackPlayer> player;

    public PlayerManager(MergedTrack mergedTrack){
        this.mergedTrack = mergedTrack;
        player = new HashMap<>();
        for(AudioTrack t : mergedTrack.getAudioTracks()){
            player.put(t.getAudioTrackType(), new TrackPlayer(t));
        }
    }

    public void startPlaying(){
        for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
            entry.getValue().play();
        }
    }

    public void startPlaying(int timeInMillis){

    }

    public void pausePlaying(){
        for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
            entry.getValue().pause();
        }
    }




}
