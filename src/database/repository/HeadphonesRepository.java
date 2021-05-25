package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Headphones;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeadphonesRepository {

    public Headphones save(Headphones headphones) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into headphones(id, name, category_id, distributor_id, price, stock, warranty)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, headphones.getProductId());
            preparedStatement.setString(2, headphones.getProductName());
            preparedStatement.setString(3, headphones.getProductCategory().getCategoryId());
            preparedStatement.setString(4, headphones.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, headphones.getPrice());
            preparedStatement.setInt(6, headphones.getStock());
            preparedStatement.setInt(7, headphones.getWarranty());

            preparedStatement.execute();

            return headphones;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the headphones: " + headphones);
        }
    }

    public List<Headphones> findAll() {

        List<Headphones> headphones = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from headphones";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                headphones.add(mapToHeadphones(resultSet));
            }
            resultSet.close();

            return headphones;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all headphones!");
        }
    }

    private Headphones mapToHeadphones(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        Headphones headphones = new Headphones(resultSet.getString(1),
                                                resultSet.getInt(6),
                                                resultSet.getString(2),
                                                category, distributor,
                                                resultSet.getInt(5),
                                                resultSet.getInt(7));

        return headphones;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_headphones_price(?, ?)}",
                    queryStock = "{? = call change_headphones_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the headphones with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from headphones where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the headphones with id: " + id);
        }
    }
}
