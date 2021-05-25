package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Fridge;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FridgeRepository {

    public Fridge save(Fridge fridge) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into fridges(id, name, category_id, distributor_id, price, stock, warranty, min_temp, max_temp, height, width, length, has_freezer)" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fridge.getProductId());
            preparedStatement.setString(2, fridge.getProductName());
            preparedStatement.setString(3, fridge.getProductCategory().getCategoryId());
            preparedStatement.setString(4, fridge.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, fridge.getPrice());
            preparedStatement.setInt(6, fridge.getStock());
            preparedStatement.setInt(7, fridge.getWarranty());
            preparedStatement.setInt(8, fridge.getMinTemperature());
            preparedStatement.setInt(9, fridge.getMaxTemperature());
            preparedStatement.setDouble(10, fridge.getHeight());
            preparedStatement.setDouble(11, fridge.getWidth());
            preparedStatement.setDouble(12, fridge.getLength());
            preparedStatement.setBoolean(13, fridge.hasFreezer());

            preparedStatement.execute();

            return fridge;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the fridge: " + fridge);
        }
    }

    public List<Fridge> findAll() {

        List<Fridge> fridges = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from fridges";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                fridges.add(mapToFridge(resultSet));
            }
            resultSet.close();

            return fridges;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all fridges!");
        }
    }

    private Fridge mapToFridge(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        Fridge fridge = new Fridge(resultSet.getString(1),
                                    resultSet.getInt(6),
                                    resultSet.getString(2),
                                    category, distributor,
                                    resultSet.getDouble(5),
                                    resultSet.getInt(7),
                                    resultSet.getInt(8),
                                    resultSet.getInt(9),
                                    resultSet.getDouble(10),
                                    resultSet.getDouble(11),
                                    resultSet.getDouble(12),
                                    resultSet.getBoolean(13));

        return fridge;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_fridge_price(?, ?)}",
                    queryStock = "{? = call change_fridge_stock(?, ?)}";

            if(price != null && price > 0.0) {
                CallableStatement callableStatement = connection.prepareCall(queryPrice);
                callableStatement.setString(2, id);
                callableStatement.setDouble(3, price);
                callableStatement.executeUpdate();
            }

            if(stock != null && stock > 0) {
                CallableStatement callableStatement = connection.prepareCall(queryStock);
                callableStatement.setString(2, id);
                callableStatement.setInt(3, stock);
                callableStatement.executeUpdate();
            }

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to update the fridge with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from fridges where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the fridge with id: " + id);
        }
    }
}
