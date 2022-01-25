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
    private AudioTrack atmosphereTrack, depthTrack, intensityTrack;
    private File f;
    private String path;

    public TrackManager(){
        trackList = new ArrayList<>();
        scanFiles();
    }

    /*Scans Files in MergedTrack Directory*/
    public void scanFiles() {
        f = new File("src/data/audio/trackLists");
        File[] fileArray = f.listFiles();
        for(int i = 0; i < fileArray.length;i++){
            if(fileArray[i].isFile()){
                path = String.valueOf(fileArray[i]);
                loadMergedTrack(path);
            }
        }
        if(trackList!=null && trackList.size()>1){
            trackList.sort((MergedTrack m1, MergedTrack m2) -> m1.getTitle().compareTo(m2.getTitle()));
        }
    }

    /*Custom Method to load MergedTrack from track-file*/
    public void loadMergedTrack(String path) {
        String mergedTrackName ="defualt Name";
        String coverPath = "defautl";
        Genre genre = null;
        long duration = 0;

        try {
            reader = new BufferedReader(new FileReader(path));
            try {
                while((line = reader.readLine()) != null) {
                    if(line.contains("NAME")){
                        mergedTrackName = getMergedTrackTitle(line);
                    }
                    if(line.contains("GENRE")){
                        genre = getGenre(line);
                    }
                    if(line.contains("COVER")){
                        coverPath = (String) getCoverPath(line);
                    }
                    if(line.contains(".mp3")) {
                        Mp3File mp3File = new Mp3File(line);
                        duration = mp3File.getLengthInSeconds();

                       if(getAudioTrackType(line).equals(AudioTrackType.ATMOSPHERE)) {
                           atmosphereTrack = new AudioTrack(line, AudioTrackType.ATMOSPHERE);
                       } else if(getAudioTrackType(line).equals(AudioTrackType.DEPTH)) {
                           depthTrack = new AudioTrack(line, AudioTrackType.DEPTH);
                       } else {
                           intensityTrack = new AudioTrack(line, AudioTrackType.INTENSITY);
                       }
                    }
                }
            } catch(IOException | UnsupportedTagException | InvalidDataException e) {
                e.printStackTrace();
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        MergedTrack mergedTrack = new MergedTrack(mergedTrackName, duration,coverPath, genre);

        mergedTrack.addTrack(atmosphereTrack);
        mergedTrack.addTrack(depthTrack);
        mergedTrack.addTrack(intensityTrack);

        this.trackList.add(mergedTrack);
    }

    private Object getCoverPath(String line) {
        return line.substring(line.indexOf(":")+1, line.length());
    }

    private Genre getGenre(String line) {
        line = line.split(":")[1];
        return Genre.valueOf(line);
    }

    private String getMergedTrackTitle(String line) {
           return line.substring(line.indexOf(":")+1, line.length());
    }

    private AudioTrackType getAudioTrackType(String line) {
        if(line.contains("atmosphere")) {
           return AudioTrackType.ATMOSPHERE;
        } else if(line.contains("depth")) {
            return AudioTrackType.DEPTH;
        } else if(line.contains("intensity")){
            return AudioTrackType.INTENSITY;
        }
        return null;
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
