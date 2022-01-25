package business.tracks;

import java.util.ArrayList;

public class MergedTrack {
    private String title;
    private Genre genre;
    private long duration;
    private ArrayList<AudioTrack> audioTracks;
    private String coverPath;

    public MergedTrack(String title, long duration,String coverPath, Genre genre){
        this(title, duration, new ArrayList<AudioTrack>(),coverPath, genre);
    }

    public MergedTrack(String title, long duration, ArrayList<AudioTrack> audioTracks, String coverPath,Genre genre){
        this.title = title;
        this.duration = duration;
        this.audioTracks = audioTracks;
        this.coverPath = coverPath;
        this.genre = genre;
    }
    public String getCoverPath(){return this.coverPath;}

    public String getTitle(){
        return this.title;
    }

    public long getDuration(){
        return this.duration;
    }

    public String getGenre() {
        return genre.toString();
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
        return title + " " + genre + " " + duration;

    }

    public void addTrack(AudioTrack track){
        this.audioTracks.add(track);
    }

}
