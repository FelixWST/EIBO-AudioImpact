package business.editing;

import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.Key;
import java.util.ArrayList;

public class KeyframeManager {

    AudioTrackType audioTrackType;
    ObservableList<Keyframe> keyframes;


    public KeyframeManager(AudioTrackType audioTrackType){
        this(audioTrackType, FXCollections.observableList(new ArrayList<Keyframe>()));
    }

    public KeyframeManager(AudioTrackType audioTrackType, ObservableList<Keyframe> keyframes){
        this.audioTrackType = audioTrackType;
        this.keyframes = keyframes;
    }

    public void addKeyframe(Keyframe newKeyframe){
        if(newKeyframe!=null) {
            if (newKeyframe.getVolume() < TrackPlayer.MIN_GAIN) {
                newKeyframe.setVolume(TrackPlayer.MIN_GAIN);
            }else if(newKeyframe.getVolume() > TrackPlayer.MAX_GAIN){
                newKeyframe.setVolume(TrackPlayer.MAX_GAIN);
            }
            if (keyframes.size() == 0) {
                keyframes.add(newKeyframe);
            } else if (!isKeyframeAlreadyExisting(newKeyframe)) {
                for (int i = 0; i < keyframes.size(); i++) {
                    if (newKeyframe.getTime() == keyframes.get(i).getTime()) {
                        keyframes.set(i, newKeyframe);
                        break;
                    } else if(newKeyframe.getTime() < keyframes.get(i).getTime()){
                        keyframes.add(i, newKeyframe);
                        break;
                    } else if (keyframes.size() > i + 1) {
                        if (newKeyframe.getTime() > keyframes.get(i).getTime() && newKeyframe.getTime() < keyframes.get(i + 1).getTime()) {
                            keyframes.add(i + 1, newKeyframe);
                            break;
                        }
                    } else {
                        keyframes.add(newKeyframe);
                        break;
                    }
                }
            }
        }
    }

    public void removeKeyframe(Keyframe keyframe){
        if(isKeyframeAlreadyExisting(keyframe)){
            for(int i = 0; i<keyframes.size(); i++){
                if(keyframes.get(i).equals(keyframe)){
                    keyframes.remove(i);
                    break;
                }
            }
        }
    }

    public Keyframe getKeyframe(int time){
        return null;
    }

    public void clearAllKeyframes(){
        for(int i = keyframes.size()-1;i>=0;i--){
            keyframes.remove(i);
        }
    }

    public boolean isKeyframeAlreadyExisting(Keyframe keyframe){
        if(keyframe!=null){
            for(Keyframe k : keyframes){
                if(k.equals(keyframe)){
                    return true;
                }else if(k.getTime() > keyframe.getTime()){
                    break;
                }
            }
        }
        return false;
    }

    public ObservableList<Keyframe> getKeyframes(){
        return this.keyframes;
    }

    public AudioTrackType getAudioTrackType(){
        return this.audioTrackType;
    }

    public float getVolumeAtTime(int millis){
        if(millis>=0){

            for(int i = 0; i< keyframes.size(); i++){
                if(keyframes.get(i).getTime()==millis){
                    return (float) keyframes.get(i).getVolume();
                }

                if(i==0 && millis < keyframes.get(i).getTime()){
                    return (float) keyframes.get(i).getVolume();
                }

                if(keyframes.size()>i+1){
                    if(millis > keyframes.get(i).getTime() && millis < keyframes.get(i+1).getTime()){
                        if(keyframes.get(i).getVolume() == keyframes.get(i+1).getVolume()){
                            return (float) keyframes.get(i).getVolume();
                        }

                        int timeDifference = keyframes.get(i+1).getTime() - keyframes.get(i).getTime();
                        double volumeDifference = (keyframes.get(i+1).getVolume()>keyframes.get(i).getVolume() ? keyframes.get(i+1).getVolume()-keyframes.get(i).getVolume() : keyframes.get(i).getVolume()-keyframes.get(i+1).getVolume());

                        double volumePerMs = volumeDifference / timeDifference;
                        int timeDelta = millis - keyframes.get(i).getTime();
                        double finalValue = timeDelta * volumePerMs;

                        if(keyframes.get(i).getVolume()>keyframes.get(i+1).getVolume()){
                            //Volume decreases
                            //Old KF value - difference @ time
                            return (float) (keyframes.get(i).getVolume()-finalValue);
                        }else{
                            //Volume Increases
                            //Old KF value + difference @ time
                            return (float) (keyframes.get(i).getVolume()+finalValue);
                        }
                    }
                }else{
                    return (float) keyframes.get(i).getVolume();
                }
            }

        }
        return TrackPlayer.DEFAULT_GAIN;
    }

}
