package business.playback;


import business.tracks.AudioTrack;
import business.tracks.MergedTrack;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class TrackPlayer {
    private SimpleMinim minim;
    private SimpleAudioPlayer audioPlayer;
    private AudioTrack audioTrack;
    private int time;
    private float volume;

    public TrackPlayer(SimpleMinim minim, SimpleAudioPlayer audioPlayer, AudioTrack audioTrack, int time) {
        this.minim = minim;
        this.audioPlayer = audioPlayer;
        this.audioTrack = audioTrack;
        this.time = time;
    }

    public void play() {
        //Thread aufrufen

    }

    public void pause() {

    }

    public void jumpTo(){

    }

    public void setVolume() {

    }
}
