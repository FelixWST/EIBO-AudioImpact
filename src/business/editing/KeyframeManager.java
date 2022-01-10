package business.editing;

import business.playback.TrackPlayer;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;

import java.util.ArrayList;

public class KeyframeManager {

    AudioTrackType audioTrackType;
    ArrayList<Keyframe> keyframes;

    public KeyframeManager(AudioTrackType audioTrackType){
        this(audioTrackType, new ArrayList<Keyframe>());
    }

    public KeyframeManager(AudioTrackType audioTrackType, ArrayList<Keyframe> keyframes){
        this.audioTrackType = audioTrackType;
        this.keyframes = keyframes;
    }

    public void addKeyframe(Keyframe newKeyframe){
        //Check TimeBounds?
        System.out.println("add keyframe");
        System.out.println(newKeyframe.toString());
        if(newKeyframe!=null) {
            if (newKeyframe.getVolume() >= TrackPlayer.MIN_GAIN && newKeyframe.getVolume() <= TrackPlayer.MAX_GAIN) {
                System.out.println("Versuche " + newKeyframe.toString() + " zu adden...");
                if (keyframes.size() == 0) {
                    keyframes.add(newKeyframe);
                    System.out.println("Empty - so just add it");
                } else if (!isKeyframeAlreadyExisting(newKeyframe)) {
                    for (int i = 0; i < keyframes.size(); i++) {
                        if (newKeyframe.getTime() == keyframes.get(i).getTime()) {
                            keyframes.set(i, newKeyframe);
                            System.out.println("Keyframe wurde ueberschrieben");
                            break;
                        } else if(newKeyframe.getTime() < keyframes.get(i).getTime()){
                            keyframes.add(i, newKeyframe);
                            break;
                        } else if (keyframes.size() > i + 1) {
                            if (newKeyframe.getTime() > keyframes.get(i).getTime() && newKeyframe.getTime() < keyframes.get(i + 1).getTime()) {
                                keyframes.add(i + 1, newKeyframe);
                                System.out.println("Keyframe wurde zwischen " + i + " und " + (i + 1) + " angefuegt");
                                break;
                            }
                        } else {
                            keyframes.add(newKeyframe);
                            System.out.println("Keyframe wurde am Ende angefügt");
                            break;
                        }
                    }
                } else {
                    System.out.println("Keyframe existiert bereits exakt so - keine ueberschreibung notwendig");
                }
            }else{
                System.out.println("Gain out of Bounds");
            }
        }
    }

    public void removeKeyframe(Keyframe keyframe){
        if(isKeyframeAlreadyExisting(keyframe)){
            for(int i = 0; i<keyframes.size(); i++){
                if(keyframes.get(i).equals(keyframe)){
                    keyframes.remove(i);
                    System.out.println("Keyframe wurde gelöscht");
                    break;
                }
            }
        }else{
            System.out.println("Keyframe existiert nicht und kann nicht gelöscht werden");
        }
    }

    public Keyframe getKeyframe(int time){
        return null;
    }

    public void clearAllKeyframes(){
        this.keyframes = new ArrayList<Keyframe>();
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

    public ArrayList<Keyframe> getKeyframes(){
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
