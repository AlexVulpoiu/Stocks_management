package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Smartwatch;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SmartwatchRepository {

    public Smartwatch save(Smartwatch smartwatch) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into smartwatches(id, name, category_id, distributor_id, price, stock, warranty)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, smartwatch.getProductId());
            preparedStatement.setString(2, smartwatch.getProductName());
            preparedStatement.setString(3, smartwatch.getProductCategory().getCategoryId());
            preparedStatement.setString(4, smartwatch.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, smartwatch.getPrice());
            preparedStatement.setInt(6, smartwatch.getStock());
            preparedStatement.setInt(7, smartwatch.getWarranty());

            preparedStatement.execute();

            return smartwatch;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the smartwatch: " + smartwatch);
        }
    }

    public List<Smartwatch> findAll() {

        List<Smartwatch> smartwatches = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from smartwatches";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                smartwatches.add(mapToSmartwatch(resultSet));
            }
            resultSet.close();

            return smartwatches;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all smartwatches!");
        }
    }

    private Smartwatch mapToSmartwatch(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        Smartwatch smartwatch = new Smartwatch(resultSet.getString(1),
                                                resultSet.getInt(6),
                                                resultSet.getString(2),
                                                category, distributor,
                                                resultSet.getInt(5),
                                                resultSet.getInt(7));

        return smartwatch;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_smartwatch_price(?, ?)}",
                    queryStock = "{? = call change_smartwatch_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the smartwatch with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from smartwatches where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the smartwatch with id: " + id);
        }
    }
}
