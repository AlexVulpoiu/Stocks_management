package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.GasCooker;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GasCookerRepository {

    public GasCooker save(GasCooker gasCooker) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into gas_cookers(id, name, category_id, distributor_id, price, stock, warranty)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gasCooker.getProductId());
            preparedStatement.setString(2, gasCooker.getProductName());
            preparedStatement.setString(3, gasCooker.getProductCategory().getCategoryId());
            preparedStatement.setString(4, gasCooker.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, gasCooker.getPrice());
            preparedStatement.setInt(6, gasCooker.getStock());
            preparedStatement.setInt(7, gasCooker.getWarranty());

            preparedStatement.execute();

            return gasCooker;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the gas cooker: " + gasCooker);
        }
    }

    public List<GasCooker> findAll() {

        List<GasCooker> gasCookers = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from gas_cookers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                gasCookers.add(mapToGasCooker(resultSet));
            }
            resultSet.close();

            return gasCookers;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all gas cookers!");
        }
    }

    private GasCooker mapToGasCooker(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        GasCooker gasCooker = new GasCooker(resultSet.getString(1),
                                            resultSet.getInt(6),
                                            resultSet.getString(2),
                                            category, distributor,
                                            resultSet.getInt(5),
                                            resultSet.getInt(7));

        return gasCooker;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_gas_cooker_price(?, ?)}",
                    queryStock = "{? = call change_gas_cooker_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the gas cooker with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from gas_cookers where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the gas cooker with id: " + id);
        }
    }
}
