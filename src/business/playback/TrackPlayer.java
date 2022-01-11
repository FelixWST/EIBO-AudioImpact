package business.playback;


import business.editing.KeyframeManager;
import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.MergedTrack;
import ddf.minim.AudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;

import java.util.HashMap;

public class TrackPlayer {
    private SimpleMinim minim;
    private SimpleAudioPlayer simpleAudioPlayer;
    private AudioTrack audioTrack;
    private KeyframeManager keyframeManager;
    private int time;
    private SimpleFloatProperty volumeProperty;
    private SimpleBooleanProperty muteProperty;
    private SimpleBooleanProperty soloProperty;
    private Thread playerThread;
    private int lastStoppedPosition = 0;
    public static final float MIN_GAIN = -80;
    public static final float MAX_GAIN = 6;
    public static final float DEFAULT_GAIN = 6;
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
            //Thread aufrufen
            if(audioTrack!=null){
                    playerThread = new Thread(){
                        public void run(){
                            simpleAudioPlayer = minim.loadMP3File(audioTrack.getPath());
                            setVolume(keyframeManager.getVolumeAtTime(simpleAudioPlayer.position()));
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
        simpleAudioPlayer.pause();
        lastStoppedPosition = simpleAudioPlayer.position();
        playerThread.interrupt();
        volumeModifierThread.interrupt();
    }

    public void playFrom(int timeInMillis){
        lastStoppedPosition = timeInMillis;
        play();
    }

    public void setVolume(float gain) {
        if(simpleAudioPlayer!=null){
            if(!muteProperty.get()){
                if(gain>=MIN_GAIN && gain<=MAX_GAIN){
                    simpleAudioPlayer.setGain(gain);
                    volumeProperty.set(gain);
                }else if(gain<MIN_GAIN){
                    System.out.println("LOWER THAN MIN");
                    simpleAudioPlayer.setGain(MIN_GAIN);
                    volumeProperty.set(MIN_GAIN);
                }else if(gain>MAX_GAIN){
                    simpleAudioPlayer.setGain(MAX_GAIN);
                    volumeProperty.set(MAX_GAIN);
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

    private class VolumeModifierThread extends Thread{
        VolumeModifierThread(){
            setDaemon(true);
        }

        @Override
        public void run(){
            while(!isInterrupted()){
                try{
                    Thread.sleep(250); // Wie oft pro sekunde soll aktualisiert werden?
                    setVolume(keyframeManager.getVolumeAtTime(simpleAudioPlayer.position()));
                }catch (InterruptedException e){
                    interrupt();
                }
            }
        }
    }
}
