package presentation.application;

import business.editing.Keyframe;
import business.editing.KeyframeManager;

public class Keyframetester {

    public static void main(String[] args){

        for(double i = -80; i<=6; i++){
            double value = Math.pow(10.0, i/20.0);
            System.out.println(i+"dB = "+value);
        }


    }
}
