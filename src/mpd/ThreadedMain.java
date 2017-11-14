package mpd;

import java.util.Random;

public class ThreadedMain {

    public static void main(String[] args) {
        int NUM_VALUES = Integer.parseInt("100000");
        MinimumPairwiseDistance mpd = new ThreadedMinimumPairwiseDistance();
        MinimumPairwiseDistance spd = new SerialMinimumPairwiseDistance();

        Random random = new Random();
        int[] values = new int[NUM_VALUES];
        for (int i = 0; i < NUM_VALUES; ++i) {
            values[i] = random.nextInt();
        }

        long startThread = System.currentTimeMillis();
        int result = mpd.minimumPairwiseDistance(values);
        System.out.println("The minimum distance was " + result);
        startThread = System.currentTimeMillis() - startThread;

        long startSerial = System.currentTimeMillis();
        int serRes = spd.minimumPairwiseDistance(values);
        System.out.println("Serial answer: " + serRes);
        startSerial = System.currentTimeMillis() - startSerial;

        System.out.println("The threaded mpd took " + startThread + "ms");
        System.out.println("The serialized mpd took " + startSerial + "ms");
    }

}
