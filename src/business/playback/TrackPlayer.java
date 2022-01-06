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

    public TrackPlayer(AudioTrack audioTrack) {
        this.minim = new SimpleMinim(true);
        this.audioTrack = audioTrack;
    }

    public void play() {
        //Thread aufrufen
        if(audioTrack!=null){
                new Thread(){
                    public void run(){
                        simpleAudioPlayer = minim.loadMP3File(audioTrack.getPath());
                        simpleAudioPlayer.play();
                    }
                }.start();
        }

    }

    public void pause() {

    }

    public void jumpTo(){

    }

    public void setVolume() {

    }
}
