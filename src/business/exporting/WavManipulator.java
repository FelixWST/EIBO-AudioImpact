package business.exporting;

import business.editing.KeyframeManager;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFileFormat.Type;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;

public class WavManipulator {
    private AudioFormat format;
    private long sampleLength;
    private double [] sampleArray;
    private KeyframeManager keyframeManager;
    private long tracklengthInMs;
    private long videoLengthInMs;

    public WavManipulator(KeyframeManager keyframeManager, long trackLengthInMs, long videoLengthInMs){
        format = null;
        sampleLength = 0;
        sampleArray = null;
        this.keyframeManager = keyframeManager;
        this.tracklengthInMs = trackLengthInMs;
        this.videoLengthInMs = videoLengthInMs;

        System.out.println("Track length: "+trackLengthInMs);
        System.out.println("Video length: "+videoLengthInMs);
    }

    public AudioFormat getFormat() {
        return format;
    }

    public long getSampleLength() {
        return sampleLength;
    }

    public double[] getSampleArray() {
        return sampleArray;
    }

    public byte[] convertSampleToByteArray(){
        boolean isBigEndian = format.isBigEndian();
        int sampleSizeInBytes = format.getSampleSizeInBits() / 8;
        byte [] byteArray = new byte[(int)(sampleLength * sampleSizeInBytes)];
        int normalizingFactor = (0x80 << (8*(sampleSizeInBytes - 1)));

        int millisecondCounter = 0;

        for(int i = 0; i < sampleLength; i++) {

            if(i > ((sampleLength/tracklengthInMs)*videoLengthInMs)){
                //End of Video
                return byteArray;
            }

            if(i % (sampleLength/tracklengthInMs) == 0){
                millisecondCounter++;
            }

            double logToLinearVolume = Math.pow(10.0, keyframeManager.getVolumeAtTime(millisecondCounter)/20.0);
            double sample = sampleArray[i] * logToLinearVolume;

            if(!(sample >= -1.0 && sample < 1.0)){
                //System.err.printf("Warning! Sample[%d] is out of range.\n", i);
                // Clipping
                sample = (sample<0)?-1.0:0.99;
            }

            int sampleInt = (int)(sample*normalizingFactor);
            //sampleInt = (int) (sampleInt * logToLinearVolume); //THIS HERE CAN CHANGE VOLUME (0-1)
            if(sampleSizeInBytes == 1) {

                byteArray[i] = sampleInt<0?(byte)(sampleInt+128):(byte)(sampleInt-128);
            } else {
                if(isBigEndian) {
                    // Big endian
                    for(int j = 0; j < sampleSizeInBytes; j++) {
                        byteArray[i*sampleSizeInBytes + j] = (byte)((sampleInt >> ((sampleSizeInBytes-j-1)*8)) & 0xff);
                    }
                } else {
                    // Little endian !!
                    for(int j = 0; j < sampleSizeInBytes; j++) {
                        byteArray[i*sampleSizeInBytes + j] = (byte)((sampleInt >> (j*8)) & 0xff);
                    }
                }
            }
        }
        System.out.println("COunted "+millisecondCounter+" times");
        return byteArray;
    }

    public void readFile(String filePath){
        try{
            File file = new File(filePath);
            System.out.println(file.toString());
            AudioInputStream in = AudioSystem.getAudioInputStream(file);
            this.format = in.getFormat();
            System.out.println("Format"+format);
            this.sampleLength = in.getFrameLength();
            BufferedInputStream bis = new BufferedInputStream(in);
            int channelNo = 0;
            convertBufferedInputStreamToSample(bis, channelNo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeFile(String filePath){
        System.out.println("Writing to : "+filePath);
        try{
            byte [] byteArray = convertSampleToByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            AudioInputStream ais = new AudioInputStream(bis, format, (sampleLength/tracklengthInMs)*videoLengthInMs);
            File file = new File(filePath);
            AudioSystem.write(ais, Type.WAVE, file);
            ais.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void convertBufferedInputStreamToSample(BufferedInputStream bis, int channelNo) {
        allocateSampleArray(sampleLength);
        boolean isBigEndian = format.isBigEndian();
        int sampleSizeInBytes = format.getSampleSizeInBits() / 8;
        byte []frameByteBuffer = new byte[format.getFrameSize()]; /* 1 Frame */
        System.out.println("Framesize: "+format.getFrameSize());
        System.out.println("Channels: "+format.getChannels());
        System.out.println("SampleSize: "+sampleSizeInBytes);
        System.out.println("Samplelen: "+sampleLength);
        int normalizingFactor = (0x80 << (8*(sampleSizeInBytes - 1)));

        //Framesize = bits per Chanel * channelNum

        /*
        # Frame
        FrameSize = (SampleSizeInBits / 8) * number_of_Channles.
        ## 2ch.
        ->|-----|<- Frame
          |L0|R0|L1|R1|...
        ->|--|<- Sample
        ## 1ch.
        ->|--|<- Frame
          |L0|L1|...
        ->|--|<- Sample

        # Sample
        ## SampleSizeInBits = 8, (sampleSizeInBytes = 1)
        ->|-------|<- Sample
          |b0...b7|b0...b7|
        ## SampleSizeInBits = 16, (sampleSizeInBytes = 2)
        ->|---------------|<- Sample
          |b0...b7b8...b15|b0...
        */
        try {
            int v;
            for (int i = 0; i < sampleLength; i++) {
                //ueber Samples iterieren
                if(bis.read(frameByteBuffer) > 0)
                {
                    v = 0;
                    // 1フレームから1サンプル分取り出し
                    if(sampleSizeInBytes == 1) {
                        //8-Bit unsigned
                        v = frameByteBuffer[0]>=0?(int)(frameByteBuffer[0]-128):(128+frameByteBuffer[0]);
                    } else {
                        //Alles ausser 8Bit bspw. 16Bit oder 24Bit
                        if(isBigEndian) {
                            // Big endian
                            for (int j = 0; j < sampleSizeInBytes; j++) {
                                v += (((int)frameByteBuffer[channelNo * sampleSizeInBytes + j] & 0xff) << (8 * (sampleSizeInBytes - j - 1)));
                            }
                        } else {
                            // Little endian
                            for (int j = 0; j < sampleSizeInBytes; j++) {
                                v += (((int)frameByteBuffer[channelNo * sampleSizeInBytes + j] & 0xff) << (8 * j));
                            }
                        }
                        if ((v & (0x80 << 8*(sampleSizeInBytes-1))) != 0) {
                            v += (0xffffffff << (8*sampleSizeInBytes));
                        }
                    }
                    sampleArray[i] = ((double) v / (double)normalizingFactor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void allocateSampleArray(long sampleLength) {
        this.sampleLength = sampleLength;
        sampleArray = new double[(int)sampleLength];
    }


}
