package business.playback;


import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.MergedTrack;
import ddf.minim.AudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

import java.util.HashMap;

public class TrackPlayer {
    private SimpleMinim minim;
    private SimpleAudioPlayer simpleAudioPlayer;
    private AudioTrack audioTrack;
    private int time;
    private float volume;
    private Thread playerThread;
    private int lastStoppedPosition = 0;
    public final float MIN_GAIN = -80;
    public final float MAX_GAIN = 6;

    public TrackPlayer(AudioTrack audioTrack) {
        this.minim = new SimpleMinim(false);
        this.audioTrack = audioTrack;
        playerThread = new Thread();
    }

    public void play() {
        if(!isPlaying()){
            //Thread aufrufen
            if(audioTrack!=null){
                    playerThread = new Thread(){
                        public void run(){
                            simpleAudioPlayer = minim.loadMP3File(audioTrack.getPath());
                            simpleAudioPlayer.play(lastStoppedPosition);
                        }
                    };
                    playerThread.start();
            }
        }
    System.out.println("CurrTimeMs:"+System.currentTimeMillis());
    }

    public void pause() {
        simpleAudioPlayer.pause();
        lastStoppedPosition = simpleAudioPlayer.position();
        playerThread.interrupt();
    }

    public void jumpTo(int timeInMillis){

    }

    public void setVolume(float gain) {
        if(simpleAudioPlayer!=null){
            if(gain>=MIN_GAIN && gain<=MAX_GAIN){
                simpleAudioPlayer.setGain(gain);
            }
        }
    }

    public boolean isPlaying(){
        return (simpleAudioPlayer==null) ? false : simpleAudioPlayer.isPlaying();
    }
}
