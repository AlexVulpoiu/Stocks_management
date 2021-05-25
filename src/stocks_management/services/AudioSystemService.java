package stocks_management.services;

import database.repository.AudioSystemRepository;
import stocks_management.product.AudioSystem;
import stocks_management.validator.Validator;

import java.util.List;

public class AudioSystemService {

    private static AudioSystemService instance = null;

    private AudioSystemService() {}

    public static AudioSystemService getInstance() {

        if(instance == null) {
            instance = new AudioSystemService();
        }
        return instance;
    }

    private AudioSystemRepository repository = new AudioSystemRepository();

    public AudioSystem add(AudioSystem audioSystem) {
        Validator validator = Validator.getInstance();

        if(validator.validateAudioSystem(audioSystem)) {
            return repository.save(audioSystem);
        } else {
            System.out.println("Can't add an invalid audio system in the database!");
            return null;
        }
    }

    public List<AudioSystem> getAll() {
        return repository.findAll();
    }

    public void update(AudioSystem audioSystem, Double price, Integer stock) {
        if(audioSystem == null) {
            System.out.println("No audio system was provided to update method!");
            return;
        }
        repository.update(audioSystem.getProductId(), price, stock);
    }

    public void delete(AudioSystem audioSystem) {
        if(audioSystem == null) {
            System.out.println("No audio system was provided to delete method!");
            return;
        }
        repository.delete(audioSystem.getProductId());
    }
}
