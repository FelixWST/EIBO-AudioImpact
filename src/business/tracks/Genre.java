package business.tracks;

public enum Genre {
    MOOD("Mood"),CINEMATIC("Cinematic"),ACTION("Action"), COMEDY("Comedy"), DRAMATIC("Dramatic"), INTENSE("Intense"), EPIC("Epic"), DARK("Dark");

    private String stringValue;

    private Genre(String stringValue){
        this.stringValue = stringValue;
    }

    public String toString(){
        return this.stringValue;
    }
}
