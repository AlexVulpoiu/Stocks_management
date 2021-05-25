package stocks_management.services;

import database.repository.HeadphonesRepository;
import stocks_management.product.Headphones;
import stocks_management.validator.Validator;

import java.util.List;

public class HeadphonesService {

    private static HeadphonesService instance = null;

    private HeadphonesService() {}

    public static HeadphonesService getInstance() {

        if(instance == null) {
            instance = new HeadphonesService();
        }
        return instance;
    }

    private HeadphonesRepository repository = new HeadphonesRepository();

    public Headphones add(Headphones headphones) {
        Validator validator = Validator.getInstance();

        if(validator.validateHeadphones(headphones)) {
            return repository.save(headphones);
        } else {
            System.out.println("Can't add an invalid gas cooker in the database!");
            return null;
        }
    }

    public List<Headphones> getAll() {
        return repository.findAll();
    }

    public void update(Headphones headphones, Double price, Integer stock) {
        if(headphones == null) {
            System.out.println("No headphones were provided to update method!");
            return;
        }
        repository.update(headphones.getProductId(), price, stock);
    }

    public void delete(Headphones headphones) {
        if(headphones == null) {
            System.out.println("No headphones were provided to delete method!");
            return;
        }
        repository.delete(headphones.getProductId());
    }
}
