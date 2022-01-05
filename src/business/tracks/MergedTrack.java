package business.tracks;

import java.util.ArrayList;

public class MergedTrack {

    private String title;
    private ArrayList<Genre> genres;
    private int duration;
    private AudioTrack[] audioTracks;


    //Cover IMAGE oder Dateipfad

    public MergedTrack(String title, int duration, AudioTrack[] audioTracks, Genre... genres){
        this.title = title;
        this.duration = duration;
        this.audioTracks = audioTracks;

        this.genres = new ArrayList<>();
        for(Genre g : genres){
            this.genres.add(g);
        }
    }

    public String getTitle(){
        return this.title;
    }

    public int getDuration(){
        return this.duration;
    }

    public AudioTrack[] getAudioTracks() {
        return this.audioTracks;
    }

}
