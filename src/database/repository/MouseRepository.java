package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Mouse;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MouseRepository {

    public Mouse save(Mouse mouse) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into mouses(id, name, category_id, distributor_id, price, stock, warranty, wireless)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mouse.getProductId());
            preparedStatement.setString(2, mouse.getProductName());
            preparedStatement.setString(3, mouse.getProductCategory().getCategoryId());
            preparedStatement.setString(4, mouse.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, mouse.getPrice());
            preparedStatement.setInt(6, mouse.getStock());
            preparedStatement.setInt(7, mouse.getWarranty());
            preparedStatement.setBoolean(8, mouse.isWireless());

            preparedStatement.execute();

            return mouse;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the mouse: " + mouse);
        }
    }

    public List<Mouse> findAll() {

        List<Mouse> mouses = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from mouses";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                mouses.add(mapToMouse(resultSet));
            }
            resultSet.close();

            return mouses;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all mouses!");
        }
    }

    private Mouse mapToMouse(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        Mouse mouse = new Mouse(resultSet.getString(1),
                                resultSet.getInt(6),
                                resultSet.getString(2),
                                category, distributor,
                                resultSet.getInt(5),
                                resultSet.getInt(7),
                                resultSet.getBoolean(8));

        return mouse;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_mouse_price(?, ?)}",
                    queryStock = "{? = call change_mouse_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the mouse with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from mouses where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the mouse with id: " + id);
        }
    }
}
