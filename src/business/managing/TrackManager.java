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

   /* public TrackManager(ArrayList<MergedTrack> trackList, int trackNumber) {
        this.trackList = trackList;
        this.trackNumber = trackNumber;
    }*/

    public void loadTestTrack(){
        AudioTrack testAtmoTrack = new AudioTrack("src/data/testData/exampleTrack/atmosphere.mp3","src/data/testData/exampleTrack/atmosphere.wav", AudioTrackType.ATMOSPHERE);
        AudioTrack testDepthTrack = new AudioTrack("src/data/testData/exampleTrack/depth.mp3","src/data/testData/exampleTrack/depth.wav", AudioTrackType.DEPTH);
        AudioTrack testIntensityTrack = new AudioTrack("src/data/testData/exampleTrack/intensity.mp3","src/data/testData/exampleTrack/intensity.wav", AudioTrackType.INTENSITY);

        MergedTrack firstMergedTrack = new MergedTrack("Test", 84000, Genre.CINEMATIC);
        firstMergedTrack.addTrack(testAtmoTrack);
        firstMergedTrack.addTrack(testDepthTrack);
        firstMergedTrack.addTrack(testIntensityTrack);

        this.trackList.add(firstMergedTrack);
    }

    public void loadLibrary() {
        AudioTrackType audioTrackType;
        String mergedTrackName ="defualt Name";
        Genre genre = null;

        try {
            reader = new BufferedReader(new FileReader("src/data/LibraryTrackList"));
            try {
                while((line = reader.readLine()) != null) {
                    if(line.contains("NAME")){
                        mergedTrackName = getMergedTrackTitle(line);
                    }
                    if(line.contains("GENRE")){
                        genre = getGenre(line);

                    }

                    if(line.contains(".mp3")) {
                        Mp3File mp3File = new Mp3File(line);
                        duration = mp3File.getLengthInSeconds();

                       if(getAudioTrackType(line).equals(AudioTrackType.ATMOSPHERE)) {
                           AtmosphereTrack = new AudioTrack(line,"", AudioTrackType.ATMOSPHERE);
                       } else if(getAudioTrackType(line).equals(AudioTrackType.DEPTH)) {
                           DepthTrack = new AudioTrack(line,"", AudioTrackType.DEPTH);
                       } else {
                           IntensityTrack = new AudioTrack(line,"", AudioTrackType.INTENSITY);
                       }
                    }
                }
                System.out.println("MergedTrack Name:"+ mergedTrackTitle);
            } catch(IOException | UnsupportedTagException | InvalidDataException e) {
                e.printStackTrace();
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        MergedTrack mergedTrack = new MergedTrack(mergedTrackName, duration, genre);

        mergedTrack.addTrack(AtmosphereTrack);
        mergedTrack.addTrack(DepthTrack);
        mergedTrack.addTrack(IntensityTrack);

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
           return mergedTrackTitle = line.substring(line.indexOf(":")+1, line.length());
    }

    private AudioTrackType getAudioTrackType(String line) {
        if(line.contains("atmosphere")) {
           return AudioTrackType.ATMOSPHERE;
        } else if(line.contains("depth")) {
            return AudioTrackType.DEPTH;
        } else {
            return AudioTrackType.INTENSITY;
        }
    }

    public void addMergedTrack(MergedTrack mergedTrack){
        this.trackList.add(mergedTrack);
    }

    public MergedTrack getMergedTrack(int index){
        return this.trackList.get(index);
    }

    public ArrayList<MergedTrack> getTrackList() {
        return this.trackList;
    }

}
