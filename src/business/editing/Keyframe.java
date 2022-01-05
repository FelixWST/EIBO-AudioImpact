package business.editing;

import java.security.KeyException;

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
}
