package business.tracks;

import java.util.ArrayList;

public class MergedTrack {

    private String title;
    private ArrayList<Genre> genres;
    private int duration;
    private ArrayList<AudioTrack> audioTracks;
    //Cover IMAGE oder Dateipfad

    public MergedTrack(String title, int duration, Genre ... genres){
        this(title, duration, new ArrayList<AudioTrack>(), genres);
    }

    public MergedTrack(String title, int duration, ArrayList<AudioTrack> audioTracks, Genre... genres){
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

    public ArrayList<AudioTrack> getAudioTracks() {
        return this.audioTracks;
    }

    public void addTrack(AudioTrack track){
        this.audioTracks.add(track);
    }
}
