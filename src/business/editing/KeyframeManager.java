package business.editing;

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
        if(newKeyframe!=null){
            System.out.println("Versuche "+newKeyframe.toString()+" zu adden...");
            if(keyframes.size() == 0){
                keyframes.add(newKeyframe);
                System.out.println("Empty - so just add it");
            }else if(!isKeyframeAlreadyExisting(newKeyframe)){
                for(int i = 0; i<keyframes.size(); i++){
                    if(newKeyframe.getTime() == keyframes.get(i).getTime()){
                        keyframes.set(i, newKeyframe);
                        System.out.println("Keyframe wurde ueberschrieben");
                        break;
                    }else if(keyframes.size() > i+1){
                        if(newKeyframe.getTime() > keyframes.get(i).getTime() && newKeyframe.getTime() < keyframes.get(i+1).getTime()){
                            keyframes.add(i+1, newKeyframe);
                            System.out.println("Keyframe wurde zwischen "+i+" und "+(i+1)+" angefuegt");
                            break;
                        }
                    }else{
                        keyframes.add(newKeyframe);
                        System.out.println("Keyframe wurde am Ende angefügt");
                        break;
                    }
                }
            }else{
                System.out.println("Keyframe existiert bereits exakt so - keine ueberschreibung notwendig");
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

}
