package business.tracks;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class MergedTrack {

    private String title;
    private ArrayList<Genre> genres;
    private long duration;
    private ArrayList<AudioTrack> audioTracks;
    private String coverPath;

    public MergedTrack(String title, long duration,String coverPath, Genre ... genres){
        this(title, duration, new ArrayList<AudioTrack>(),coverPath, genres);
    }

    public MergedTrack(String title, long duration, ArrayList<AudioTrack> audioTracks, String coverPath,Genre... genres){
        this.title = title;
        this.duration = duration;
        this.audioTracks = audioTracks;
        this.coverPath = coverPath;

        this.genres = new ArrayList<>();
        for(Genre g : genres){
            this.genres.add(g);
        }
    }
    public String getCoverPath(){return this.coverPath;}

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
