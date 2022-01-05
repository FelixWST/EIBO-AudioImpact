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

    public AudioTrackType getAudioTrackType(){
        return this.audioTrackType;
    }
}
