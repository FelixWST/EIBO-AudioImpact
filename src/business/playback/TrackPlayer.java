package business.playback;


import business.tracks.AudioTrack;
import business.tracks.MergedTrack;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class TrackPlayer {
    private SimpleMinim minim;
    private SimpleAudioPlayer audioPlayer;
    private MergedTrack mergedTrack;
    private int time;
    private float volume;

    public TrackPlayer(MergedTrack mergedTrack) {
        this.minim = new SimpleMinim(true);
        this.mergedTrack = mergedTrack;
    }

    public void play() {
        //Thread aufrufen
        if(mergedTrack.getAudioTracks()!=null){
            for(AudioTrack t : mergedTrack.getAudioTracks()){
                new Thread(){
                    public void run(){
                        audioPlayer = minim.loadMP3File(t.getPath());
                        audioPlayer.play();
                    }
                }.start();
            }
        }

    }

    public void pause() {

    }

    public void jumpTo(){

    }

    public void setVolume() {

    }
}
