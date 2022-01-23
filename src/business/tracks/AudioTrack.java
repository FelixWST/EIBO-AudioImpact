package business.tracks;

public class AudioTrack {
    private String path;
    private AudioTrackType audioTrackType;

    public AudioTrack(String path, AudioTrackType audioTrackType) {
        this.path = path;
        this.audioTrackType = audioTrackType;
    }

    public String getPath() {
        return this.path;
    }

    public String getWavPath(){
        return this.path.split("\\.")[0]+".wav";
    }

    public AudioTrackType getAudioTrackType(){
        return this.audioTrackType;
    }
}
