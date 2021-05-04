package stocks_management.services;

import stocks_management.product.AudioSpeaker;

import java.util.ArrayList;
import java.util.List;

public class ReadingService {

    private static ReadingService instance = null;

    public static ReadingService getInstance() {

        if(instance == null) {
            instance = new ReadingService();
        }
        return instance;
    }

    private ReadingService() {}

    public List<AudioSpeaker> readAudioSpeakers() {

        List<AudioSpeaker> audioSpeakers = new ArrayList<AudioSpeaker>();
        return audioSpeakers;
    }
}
