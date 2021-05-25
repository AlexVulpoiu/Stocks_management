package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.TV;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TVRepository {

    public TV save(TV tv) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into tvs(id, name, category_id, distributor_id, price, stock, warranty, diagonal, resolution, room)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tv.getProductId());
            preparedStatement.setString(2, tv.getProductName());
            preparedStatement.setString(3, tv.getProductCategory().getCategoryId());
            preparedStatement.setString(4, tv.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, tv.getPrice());
            preparedStatement.setInt(6, tv.getStock());
            preparedStatement.setInt(7, tv.getWarranty());
            preparedStatement.setDouble(8, tv.getDiagonal());
            preparedStatement.setString(9, tv.getResolution());
            preparedStatement.setString(10, tv.getRoom());

            preparedStatement.execute();

            return tv;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the tv: " + tv);
        }
    }

    public List<TV> findAll() {

        List<TV> tvs = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from tvs";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                tvs.add(mapToTV(resultSet));
            }
            resultSet.close();

            return tvs;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all tvs!");
        }
    }

    private TV mapToTV(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        TV tv = new TV(resultSet.getString(1),
                        resultSet.getInt(6),
                        resultSet.getString(2),
                        category, distributor,
                        resultSet.getInt(5),
                        resultSet.getInt(7),
                        resultSet.getDouble(8),
                        resultSet.getString(9));

        return tv;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_tv_price(?, ?)}",
                    queryStock = "{? = call change_tv_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the tv with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from tvs where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the tv with id: " + id);
        }
    }
}
