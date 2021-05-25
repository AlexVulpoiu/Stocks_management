package stocks_management.services;

import database.repository.PowerBankRepository;
import stocks_management.product.PowerBank;
import stocks_management.validator.Validator;

import java.util.List;

public class PowerBankService {

    private static PowerBankService instance = null;

    private PowerBankService() {}

    public static PowerBankService getInstance() {

        if(instance == null) {
            instance = new PowerBankService();
        }
        return instance;
    }

    private PowerBankRepository repository = new PowerBankRepository();

    public PowerBank add(PowerBank powerBank) {
        Validator validator = Validator.getInstance();

        if(validator.validatePowerBank(powerBank)) {
            return repository.save(powerBank);
        } else {
            System.out.println("Can't add an invalid power bank in the database!");
            return null;
        }
    }

    public List<PowerBank> getAll() {
        return repository.findAll();
    }

    public void update(PowerBank powerBank, Double price, Integer stock) {
        if(powerBank == null) {
            System.out.println("No power bank was provided to update method!");
            return;
        }
        repository.update(powerBank.getProductId(), price, stock);
    }

    public void delete(PowerBank powerBank) {
        if(powerBank == null) {
            System.out.println("No power bank was provided to delete method!");
            return;
        }
        repository.delete(powerBank.getProductId());
    }
}
