package stocks_management.validator;

public class Validator {

    public boolean validateName(String name) {
        return name.matches("^[a-z]{3,}$");
    }

    public boolean validatePrice(double price) {
        return price > 0.0;
    }

    public boolean validatePercent(double percent) {
        return percent > -1.0;
    }
}
