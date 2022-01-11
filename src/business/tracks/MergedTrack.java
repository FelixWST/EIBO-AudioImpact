package business.tracks;

import java.util.ArrayList;

public class MergedTrack {

    private String title;
    private ArrayList<Genre> genres;
    private long duration;
    private ArrayList<AudioTrack> audioTracks;
    //Cover IMAGE oder Dateipfad

    public MergedTrack(String title, long duration, Genre ... genres){
        this(title, duration, new ArrayList<AudioTrack>(), genres);
    }

    public MergedTrack(String title, long duration, ArrayList<AudioTrack> audioTracks, Genre... genres){
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

    public long getDuration(){
        return this.duration;
    }

    public String getGenre() {
        return this.genres.toString();
    }

    public ArrayList<AudioTrack> getAudioTracks() {
        return this.audioTracks;
    }

    public AudioTrack getAudioTrack(AudioTrackType audioTrackType){
        for(AudioTrack at : audioTracks){
            if(at.getAudioTrackType() == audioTrackType){
                return at;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return title + " " + genres + " " + duration;

    }

    public void addTrack(AudioTrack track){
        this.audioTracks.add(track);
    }

}
