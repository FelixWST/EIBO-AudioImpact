package presentation.application;

import business.editing.Keyframe;
import business.editing.KeyframeManager;

public class Keyframetester {

    public static void main(String[] args){

        KeyframeManager keyframeManager = new KeyframeManager(null);
        System.out.println(keyframeManager.getKeyframes().toString());
        keyframeManager.addKeyframe(new Keyframe(2, 0.4));
        System.out.println(keyframeManager.getKeyframes().toString());
        keyframeManager.addKeyframe(new Keyframe(4, 0.6));
        System.out.println(keyframeManager.getKeyframes().toString());
        keyframeManager.addKeyframe(new Keyframe(3, 0.1));
        System.out.println(keyframeManager.getKeyframes().toString());
        keyframeManager.addKeyframe(new Keyframe(3, 0.1));
        System.out.println(keyframeManager.getKeyframes().toString());
        keyframeManager.addKeyframe(new Keyframe(3, 0.2));
        System.out.println(keyframeManager.getKeyframes().toString());

        keyframeManager.removeKeyframe(new Keyframe(3, 0.8));
        System.out.println(keyframeManager.getKeyframes().toString());

        keyframeManager.removeKeyframe(new Keyframe(3, 0.2));
        System.out.println(keyframeManager.getKeyframes().toString());

        keyframeManager.clearAllKeyframes();
    }
}
