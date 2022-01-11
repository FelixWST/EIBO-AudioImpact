package business.exporting;

import business.editing.KeyframeManager;

public class WaveExporter {

    KeyframeManager keyframeManager;

    public WaveExporter(KeyframeManager keyframeManager){
        this.keyframeManager = keyframeManager;
    }

    public void export(){
        WaveData waveData = new WaveData(keyframeManager);

        //Input funktioniert mit Mono 16Bit
        waveData.readFile("src/data/testData/exampleTrack/depth-Ohne Titel Allgemeine Audioformate.wav");
        waveData.writeFile("./outputTest.wav");
    }
}
