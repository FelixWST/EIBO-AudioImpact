package business.managing;

import business.editing.KeyframeManager;
import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.MergedTrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    MergedTrack mergedTrack;
    HashMap<AudioTrackType, TrackPlayer> player;
    HashMap<AudioTrackType, KeyframeManager> keyframeManagers;


    public PlayerManager(MergedTrack mergedTrack, ArrayList<KeyframeManager> keyframeManagers){
        this.mergedTrack = mergedTrack;

        this.keyframeManagers = new HashMap<>();
        for(KeyframeManager kfm : keyframeManagers){
            this.keyframeManagers.put(kfm.getAudioTrackType(), kfm);
        }

        player = new HashMap<>();
        for(AudioTrack t : mergedTrack.getAudioTracks()){
            player.put(t.getAudioTrackType(), new TrackPlayer(t, this.keyframeManagers.get(t.getAudioTrackType())));
        }

    }

    public void startPlaying(){
        for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
            entry.getValue().play();
        }
    }

    public void startPlaying(int timeInMillis){
        for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
            entry.getValue().playFrom(timeInMillis);
        }
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
        player.get(trackType).mute();
        //Track Muten, darf aber keinen einfluss auf Automation haben
    }

    public void toggleSoloOnTrack(AudioTrackType trackType){
        //Track Solo = rest auf Mute
    }

    public TrackPlayer getTrackPlayer(AudioTrackType audioTrackType){
        return player.get(audioTrackType);
    }




}
