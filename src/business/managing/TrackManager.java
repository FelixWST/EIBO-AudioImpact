package business.managing;

import business.tracks.AudioTrack;
import business.tracks.AudioTrackType;
import business.tracks.Genre;
import business.tracks.MergedTrack;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.*;
import java.util.ArrayList;

public class TrackManager {

    private ArrayList<MergedTrack> trackList;
    private int trackNumber;
    private BufferedReader reader;
    private String line;
    private long duration;
    private String mergedTrackTitle;
    private AudioTrack AtmosphereTrack;
    private AudioTrack DepthTrack;
    private AudioTrack IntensityTrack;

    public TrackManager(){
        trackList = new ArrayList<>();
    }

    public TrackManager(ArrayList<MergedTrack> trackList, int trackNumber) {
        this.trackList = trackList;
        this.trackNumber = trackNumber;
    }

    public void loadTestTrack(){
        AudioTrack testAtmoTrack = new AudioTrack("src/data/testData/exampleTrack/atmosphere.mp3", AudioTrackType.ATMOSPHERE);
        AudioTrack testDepthTrack = new AudioTrack("src/data/testData/exampleTrack/depth.mp3", AudioTrackType.DEPTH);
        AudioTrack testIntensityTrack = new AudioTrack("src/data/testData/exampleTrack/intensity.mp3", AudioTrackType.INTENSITY);

        MergedTrack firstMergedTrack = new MergedTrack("Test", 200, Genre.CINEMATIC);
        firstMergedTrack.addTrack(testAtmoTrack);
        firstMergedTrack.addTrack(testDepthTrack);
        firstMergedTrack.addTrack(testIntensityTrack);

        this.trackList.add(firstMergedTrack);

    }

    public void loadLibrary() {

        try {
            reader = new BufferedReader(new FileReader("src/data/LibraryTrackList"));
            try {
                while((line = reader.readLine()) != null) {

                    if(line.contains(".mp3")) {
                        Mp3File mp3File = new Mp3File(line);
                        duration = mp3File.getLengthInSeconds();

                       if(getAudtioTrackType(line).equals(AudioTrackType.ATMOSPHERE)) {
                           AtmosphereTrack = new AudioTrack(line, AudioTrackType.ATMOSPHERE);
                       } else if(getAudtioTrackType(line).equals(AudioTrackType.DEPTH)) {
                           DepthTrack = new AudioTrack(line, AudioTrackType.DEPTH);
                       } else {
                           IntensityTrack = new AudioTrack(line, AudioTrackType.INTENSITY);
                       }
                    }
                }
            } catch(IOException | UnsupportedTagException | InvalidDataException e) {
                e.printStackTrace();
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        MergedTrack mergedTrack = new MergedTrack(getMergedTrackTitle(line), duration, getGenre(line));

        mergedTrack.addTrack(AtmosphereTrack);
        mergedTrack.addTrack(DepthTrack);
        mergedTrack.addTrack(IntensityTrack);

       //addMergedTrack(mergedTrack);
        this.trackList.add(mergedTrack);
        System.out.println("Merged Track added");
    }

    private Genre getGenre(String line) {
        if(line.contains("CINEMATIC")) {
            return Genre.CINEMATIC;
        } else if(line.contains("ACTION")) {
            return Genre.ACTION;
        } else if (line.contains("MOOD")) {
            return Genre.MOOD;
        } else {
            return Genre.COMEDY;
        }
    }

    private String getMergedTrackTitle(String line) {
        if(line.contains("NAME:")){
            mergedTrackTitle = line.substring(line.indexOf("NAME:"), (line.indexOf("NAME:") + line.indexOf(" ")));
            return mergedTrackTitle;
        } else {
            return mergedTrackTitle = "defaultName";
        }
    }

    private AudioTrackType getAudtioTrackType(String line) {
        if(line.contains("atmosphere")) {
           return AudioTrackType.ATMOSPHERE;
        } else if(line.contains("depth")) {
            return AudioTrackType.DEPTH;
        } else {
            return AudioTrackType.INTENSITY;
        }
    }

    public int getTrackNumber() {
        return this.trackNumber;
    }

    public void addMergedTrack(MergedTrack mergedTrack){
        this.trackList.add(mergedTrack);
    }

    public MergedTrack getMergedTrack(int index){
        return this.trackList.get(index);
    }


}
