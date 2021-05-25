package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.PowerBank;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PowerBankRepository {

    public PowerBank save(PowerBank powerBank) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into power_banks(id, name, category_id, distributor_id, price, stock, warranty, capacity)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, powerBank.getProductId());
            preparedStatement.setString(2, powerBank.getProductName());
            preparedStatement.setString(3, powerBank.getProductCategory().getCategoryId());
            preparedStatement.setString(4, powerBank.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, powerBank.getPrice());
            preparedStatement.setInt(6, powerBank.getStock());
            preparedStatement.setInt(7, powerBank.getWarranty());
            preparedStatement.setInt(8, powerBank.getCapacity());

            preparedStatement.execute();

            return powerBank;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the power bank: " + powerBank);
        }
    }

    public List<PowerBank> findAll() {

        List<PowerBank> powerBanks = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from power_banks";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                powerBanks.add(mapToPowerBank(resultSet));
            }
            resultSet.close();

            return powerBanks;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all power banks!");
        }
    }

    private PowerBank mapToPowerBank(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        PowerBank powerBank = new PowerBank(resultSet.getString(1),
                                            resultSet.getInt(6),
                                            resultSet.getString(2),
                                            category, distributor,
                                            resultSet.getInt(5),
                                            resultSet.getInt(7),
                                            resultSet.getInt(8));

        return powerBank;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_power_bank_price(?, ?)}",
                    queryStock = "{? = call change_power_bank_stock(?, ?)}";

            if(price != null && price > 0.0) {
                CallableStatement callableStatement = connection.prepareCall(queryPrice);
                callableStatement.setString(2, id);
                callableStatement.setDouble(3, price);
                callableStatement.executeUpdate();
            }

            if(stock != null && stock >= 0) {
                CallableStatement callableStatement = connection.prepareCall(queryStock);
                callableStatement.setString(2, id);
                callableStatement.setInt(3, stock);
                callableStatement.executeUpdate();
            }

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to update the power bank with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from power_banks where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the power bank with id: " + id);
        }
    }
}
