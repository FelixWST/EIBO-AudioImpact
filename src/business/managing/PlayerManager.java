package business.managing;

import business.editing.KeyframeManager;
import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.MergedTrack;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private MergedTrack mergedTrack;
    private HashMap<AudioTrackType, TrackPlayer> player;
    private HashMap<AudioTrackType, KeyframeManager> keyframeManagers;
    private SimpleDoubleProperty totalVolumeProperty;


    public PlayerManager(){
        this(null,null);
    }

    public PlayerManager(MergedTrack mergedTrack, ArrayList<KeyframeManager> keyframeManagers){
        if(mergedTrack!=null && keyframeManagers != null){
            this.mergedTrack = mergedTrack;

            this.keyframeManagers = new HashMap<>();
            for(KeyframeManager kfm : keyframeManagers){
                this.keyframeManagers.put(kfm.getAudioTrackType(), kfm);
            }

            player = new HashMap<>();
            if(mergedTrack!=null){
                for(AudioTrack t : mergedTrack.getAudioTracks()){
                    player.put(t.getAudioTrackType(), new TrackPlayer(t, this.keyframeManagers.get(t.getAudioTrackType())));
                }
            }
        }
        this.totalVolumeProperty = new SimpleDoubleProperty(1);

    }

    public void changeMergedTrack(MergedTrack mergedTrack, ArrayList<KeyframeManager> keyframeManagers){
        pausePlaying();
        this.mergedTrack = mergedTrack;

        this.keyframeManagers = new HashMap<>();
        for(KeyframeManager kfm : keyframeManagers){
            this.keyframeManagers.put(kfm.getAudioTrackType(), kfm);
        }

        player = new HashMap<>();
        if(mergedTrack!=null){
            for(AudioTrack t : mergedTrack.getAudioTracks()){
                player.put(t.getAudioTrackType(), new TrackPlayer(t, this.keyframeManagers.get(t.getAudioTrackType())));
            }
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
    }

    public void toggleSoloOnTrack(AudioTrackType trackType){
        if(player.get(trackType).soloProperty().get()){
            player.get(trackType).soloProperty().set(false);
            if(isAnotherPlayerSolo()){
                player.get(trackType).mute();
            }
            for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
                if(entry.getValue().muteProperty().get() && !isAnotherPlayerSolo()){
                    //Nur wenn kein player mehr auf Solo ist
                    entry.getValue().mute();
                }
            }
        }else{
            player.get(trackType).soloProperty().set(true);
            if(player.get(trackType).muteProperty().get()){
                player.get(trackType).mute();
            }
            for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
                if(entry.getKey()!=trackType){
                    if(!entry.getValue().muteProperty().get() && !entry.getValue().soloProperty().get()){
                        entry.getValue().mute();
                    }
                }
            }
        }
    }

    public boolean isAnotherPlayerSolo(){
        for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
            if(entry.getValue().soloProperty().get()){
               return true;
            }
        }
        return false;
    }

    public void setTotalVolumeProperty(double volumeProperty){
        this.totalVolumeProperty.set(volumeProperty);
        for(Map.Entry<AudioTrackType, TrackPlayer> entry : player.entrySet()){
            entry.getValue().setTotalVolumeModifier((float) Math.abs(totalVolumeProperty.get()));
        }
    }

    public SimpleDoubleProperty totalVolumeProperty(){
        return this.totalVolumeProperty;
    }

    public TrackPlayer getTrackPlayer(AudioTrackType audioTrackType){
        return player.get(audioTrackType);
    }





}
