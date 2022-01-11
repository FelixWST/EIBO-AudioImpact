package business.tracks;

public class AudioTrack {
    private String path;
    private String wavPath;
    private AudioTrackType audioTrackType;

    public AudioTrack(String path, String wavPath, AudioTrackType audioTrackType) {
        this.path = path;
        this.wavPath = wavPath;
        this.audioTrackType = audioTrackType;
    }

    public String getPath() {
        return this.path;
    }

    public String getWavPath(){
        return this.wavPath;
    }

    public AudioTrackType getAudioTrackType(){
        return this.audioTrackType;
    }
}
