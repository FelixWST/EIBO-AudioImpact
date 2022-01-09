package business.editing;

public class Keyframe {
    private int time;
    private double volume;

    public Keyframe(int time, double volume){
        this.time = time;
        this.volume = volume;
    }
    public int getTime() {
        return time;
    }

    public double getVolume() {
        return volume;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean equals(Keyframe keyframe){
        return keyframe.getTime() == this.time && keyframe.getVolume() == this.volume;
    }

    public String toString(){
        return "Keyframe at time "+time+" with volume "+volume;
    }
}
