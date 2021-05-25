package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    public Category save(Category category) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into categories(id, name) values(?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getCategoryId());
            preparedStatement.setString(2, category.getCategoryName());

            preparedStatement.execute();

            return category;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the category: " + category);
        }
    }

    public List<Category> findAll() {

        List<Category> categories = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from categories";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                categories.add(mapToCategory(resultSet));
            }

            resultSet.close();
            return categories;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all categories!");
        }
    }

    private Category mapToCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category(resultSet.getString(1), resultSet.getString(2));
        return category;
    }

    public boolean update(String id, String name) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "{? = call change_name(?, ?)}";

            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setString(2, id);
            callableStatement.setString(3, name);
            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.executeUpdate();
            int response = callableStatement.getByte(1);

            return response == 1;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to update the category with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from categories where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
                throw new RuntimeException("Something went wrong while trying to delete the category with id: " + id);
        }
    }
}
