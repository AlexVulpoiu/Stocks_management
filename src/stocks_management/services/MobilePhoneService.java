package stocks_management.services;

import database.repository.MobilePhoneRepository;
import stocks_management.product.MobilePhone;
import stocks_management.validator.Validator;

import java.util.List;

public class MobilePhoneService {

    private static MobilePhoneService instance = null;

    private MobilePhoneService() {}

    public static MobilePhoneService getInstance() {
        if(instance == null) {
            instance = new MobilePhoneService();
        }
        return instance;
    }

    private MobilePhoneRepository repository = new MobilePhoneRepository();

    public MobilePhone add(MobilePhone mobilePhone) {
        Validator validator = Validator.getInstance();

        if(validator.validateMobilePhone(mobilePhone)) {
            return repository.save(mobilePhone);
        } else {
            System.out.println("Can't add an invalid mobile phone in the database!");
            return null;
        }
    }

    public List<MobilePhone> getAll() {
        return repository.findAll();
    }

    public void update(MobilePhone mobilePhone, Double price, Integer stock) {
        if(mobilePhone == null) {
            System.out.println("No mobile phone was provided to update method!");
            return;
        }
        repository.update(mobilePhone.getProductId(), price, stock);
    }

    public void delete(MobilePhone mobilePhone) {
        if(mobilePhone == null) {
            System.out.println("No mobile phone was provided to delete method!");
            return;
        }
        repository.delete(mobilePhone.getProductId());
    }
}
