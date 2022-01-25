package business.editing;

import business.playback.TrackPlayer;
import business.tracks.AudioTrackType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class KeyframeManager{

    private AudioTrackType audioTrackType;
    private ObservableList<Keyframe> keyframes;


    public KeyframeManager(AudioTrackType audioTrackType){
        this(audioTrackType, FXCollections.observableList(new ArrayList<Keyframe>()));
    }

    public KeyframeManager(AudioTrackType audioTrackType, ObservableList<Keyframe> keyframes){
        this.audioTrackType = audioTrackType;
        this.keyframes = keyframes;
    }

    /*Add new Keyframe*/
    public void addKeyframe(Keyframe newKeyframe){
        if(newKeyframe!=null) {
            //Check Volume Boundaries
            if (newKeyframe.getVolume() < TrackPlayer.MIN_GAIN) {
                newKeyframe.setVolume(TrackPlayer.MIN_GAIN);
            }else if(newKeyframe.getVolume() > TrackPlayer.MAX_GAIN){
                newKeyframe.setVolume(TrackPlayer.MAX_GAIN);
            }
            //First Keyframe, just add it
            if (keyframes.size() == 0) {
                keyframes.add(newKeyframe);
            } else if (!isKeyframeAlreadyExisting(newKeyframe)) {
                for (int i = 0; i < keyframes.size(); i++) {
                    //There is already a Keyframe at that time, but with another volume -> override it
                    if (newKeyframe.getTime() == keyframes.get(i).getTime()) {
                        keyframes.set(i, newKeyframe);
                        break;
                    //Time of new Keyframe is before the current iteration, so we can just add it
                    } else if(newKeyframe.getTime() < keyframes.get(i).getTime()){
                        keyframes.add(i, newKeyframe);
                        break;
                    //If there is another Keyframe coming, check if time is between those both, if not keep on iterating
                    } else if (keyframes.size() > i + 1) {
                        if (newKeyframe.getTime() > keyframes.get(i).getTime() && newKeyframe.getTime() < keyframes.get(i + 1).getTime()) {
                            keyframes.add(i + 1, newKeyframe);
                            break;
                        }
                    } else {
                        //Just add the Keyframe at the end
                        keyframes.add(newKeyframe);
                        break;
                    }
                }
            }
        }
    }

    /*Removes a Keyframe*/
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

    /*Removes all Keyframes*/
    public void clearAllKeyframes(){
        for(int i = keyframes.size()-1;i>=0;i--){
            keyframes.remove(i);
        }
    }

    /*Checks if exactly this time & value combination already exitsts*/
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

    /*Calculates the Playback volume to a given time and interpolates the value*/
    public float getVolumeAtTime(int millis){
        if(millis>=0){
            for(int i = 0; i< keyframes.size(); i++){
                //If time matches with an Keyframe, just return Keyframe Value
                if(keyframes.get(i).getTime()==millis){
                    return (float) keyframes.get(i).getVolume();
                }

                //If we are before the first Keyframe, return the value of the first Keyframe
                if(i==0 && millis < keyframes.get(i).getTime()){
                    return (float) keyframes.get(i).getVolume();
                }

                if(keyframes.size()>i+1){
                    // Time is between two Keyframes
                    if(millis > keyframes.get(i).getTime() && millis < keyframes.get(i+1).getTime()){
                        //If Both Keyframes have same volume, return either of them
                        if(keyframes.get(i).getVolume() == keyframes.get(i+1).getVolume()){
                            return (float) keyframes.get(i).getVolume();
                        }

                        //Calculates timesoan and volume Difference between the two Keyframes
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

    public String toString(){
        String keyframeString = "";
        for(Keyframe kf : this.keyframes){
            keyframeString += kf.toString();
        }
        return "KeyframeManager for Audiotrack "+audioTrackType+". Keyframes: "+keyframeString;
    }

    public ObservableList<Keyframe> getKeyframes(){
        return this.keyframes;
    }

    public AudioTrackType getAudioTrackType(){
        return this.audioTrackType;
    }

}
