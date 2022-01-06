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

    public void setTrackVolume(AudioTrackType trackType, float gain){
        if(player.containsKey(trackType) && player.get(trackType)!=null){
            player.get(trackType).setVolume(gain);
        }
    }

    public void toggleMuteOnTrack(AudioTrackType trackType){
        //Track Muten, darf aber keinen einfluss auf Automation haben
    }

    public void toggleSoloOnTrack(AudioTrackType trackType){
        //Track Solo = rest auf Mute
    }




}
