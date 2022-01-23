package business.tracks;

public enum Genre {
    MOOD("Mood"),CINEMATIC("Cinematic"),ACTION("Action"), COMEDY("Comedy");

    private String stringValue;

    private Genre(String stringValue){
        this.stringValue = stringValue;
    }

    public String toString(){
        return this.stringValue;
    }
}
