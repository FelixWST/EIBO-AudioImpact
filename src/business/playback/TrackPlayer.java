package business.playback;

import business.editing.KeyframeManager;
import business.tracks.AudioTrack;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;

public class TrackPlayer {
    public static final float MIN_GAIN = -80;
    public static final float MAX_GAIN = 6;
    public static final float DEFAULT_GAIN = 6;

    private SimpleMinim minim;
    private SimpleAudioPlayer simpleAudioPlayer;
    private AudioTrack audioTrack;
    private KeyframeManager keyframeManager;
    private SimpleFloatProperty volumeProperty;
    private SimpleBooleanProperty muteProperty;
    private SimpleBooleanProperty soloProperty;
    private Thread playerThread;
    private int lastStoppedPosition = 0;
    private float totalVolumeModifier = 0;
    private VolumeModifierThread volumeModifierThread;

    public TrackPlayer(AudioTrack audioTrack, KeyframeManager keyframeManager) {
        this.minim = new SimpleMinim(false);
        this.audioTrack = audioTrack;
        this.keyframeManager = keyframeManager;
        this.playerThread = new Thread();
        this.volumeModifierThread = new VolumeModifierThread();
        this.volumeProperty = new SimpleFloatProperty();
        volumeProperty.set(keyframeManager.getVolumeAtTime(lastStoppedPosition));
        this.muteProperty = new SimpleBooleanProperty(false);
        this.soloProperty = new SimpleBooleanProperty(false);
    }

    public void play() {
        if(!isPlaying()){
            if(audioTrack!=null){
                    playerThread = new Thread(){
                        public void run(){
                            simpleAudioPlayer = minim.loadMP3File(audioTrack.getPath());
                            setVolume(keyframeManager.getVolumeAtTime(lastStoppedPosition));
                            simpleAudioPlayer.play(lastStoppedPosition);
                        }
                    };
                    playerThread.start();
                    volumeModifierThread = new VolumeModifierThread();
                    volumeModifierThread.start();
            }
        }
    }

    public void pause() {
        if(simpleAudioPlayer!=null){
            simpleAudioPlayer.pause();
            lastStoppedPosition = simpleAudioPlayer.position();
            playerThread.interrupt();
            volumeModifierThread.interrupt();
        }
    }

    public void playFrom(int timeInMillis){
        lastStoppedPosition = timeInMillis;
        play();
    }

    public void setVolume(float gain){
        float trackGain = gain;
        //Increase or Decrease Playback Volume by monitor volume
        gain -= totalVolumeModifier;
        if(simpleAudioPlayer!=null){
            if(!muteProperty.get()){
                //Check Volume Bounds
                if(gain>=MIN_GAIN && gain<=MAX_GAIN){
                    simpleAudioPlayer.setGain(gain);
                    volumeProperty.set(trackGain);
                }else if(gain<MIN_GAIN){
                    simpleAudioPlayer.setGain(MIN_GAIN);
                    volumeProperty.set(trackGain);
                }else if(gain>MAX_GAIN){
                    simpleAudioPlayer.setGain(MAX_GAIN);
                    volumeProperty.set(trackGain);
                }
            }else{
                simpleAudioPlayer.setGain(MIN_GAIN);
                volumeProperty.set(MIN_GAIN);
            }
        }
    }

    public int getPosition(){
        return (simpleAudioPlayer!=null) ? simpleAudioPlayer.position() : 0;
    }

    public void mute(){
        muteProperty.set(!muteProperty.get());
    }

    public boolean isPlaying(){
        return (simpleAudioPlayer==null) ? false : simpleAudioPlayer.isPlaying();
    }

    public SimpleFloatProperty volumeProperty() {
        return volumeProperty;
    }

    public SimpleBooleanProperty muteProperty() {
        return muteProperty;
    }

    public SimpleBooleanProperty soloProperty(){
        return this.soloProperty;
    }

    public void setTotalVolumeModifier(float totalVolumeModifier){
        this.totalVolumeModifier = totalVolumeModifier;
    }

    public void updateVolumeAtCurrentTime(){
        if(simpleAudioPlayer!=null){
            setVolume(keyframeManager.getVolumeAtTime(simpleAudioPlayer.position()));
        }
    }

    /*Custom Thread that updates the Volume every x ms with the interpolated Keyframe Values*/
    private class VolumeModifierThread extends Thread{
        VolumeModifierThread(){
            setDaemon(true);
        }

        @Override
        public void run(){
            while(!isInterrupted()){
                try{
                    if(simpleAudioPlayer!=null){
                        setVolume(keyframeManager.getVolumeAtTime(simpleAudioPlayer.position()));
                    }
                    Thread.sleep(100); // UpdateInterval for Volume while Playing
                }catch (InterruptedException e){
                    interrupt();
                }
            }
        }
    }
}
