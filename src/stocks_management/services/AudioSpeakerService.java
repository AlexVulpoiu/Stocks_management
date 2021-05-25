package stocks_management.services;

import database.repository.AudioSpeakerRepository;
import stocks_management.product.AudioSpeaker;
import stocks_management.validator.Validator;

import java.util.List;

public class AudioSpeakerService {

    private static AudioSpeakerService instance = null;

    private AudioSpeakerService() {}

    public static AudioSpeakerService getInstance() {

        if(instance == null) {
            instance = new AudioSpeakerService();
        }
        return instance;
    }

    private AudioSpeakerRepository repository = new AudioSpeakerRepository();

    public AudioSpeaker add(AudioSpeaker audioSpeaker) {
        Validator validator = Validator.getInstance();

        if(validator.validateAudioSpeaker(audioSpeaker)) {
            return repository.save(audioSpeaker);
        } else {
            System.out.println("Can't add an invalid fridge in the database!");
            return null;
        }
    }

    public List<AudioSpeaker> getAll() {
        return repository.findAll();
    }

    public void update(AudioSpeaker audioSpeaker, Double price, Integer stock) {
        if(audioSpeaker == null) {
            System.out.println("No audio speaker was provided to update method!");
            return;
        }
        repository.update(audioSpeaker.getProductId(), price, stock);
    }

    public void delete(AudioSpeaker audioSpeaker) {
        if(audioSpeaker == null) {
            System.out.println("No audio speaker was provided to delete method!");
            return;
        }
        repository.delete(audioSpeaker.getProductId());
    }
}
