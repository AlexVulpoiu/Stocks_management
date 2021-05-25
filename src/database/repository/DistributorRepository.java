package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.distributor.Distributor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistributorRepository {

    public Distributor save(Distributor distributor) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into stocks_management.distributors(id, name, country) values(?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, distributor.getDistributorId());
            preparedStatement.setString(2, distributor.getDistributorName());
            preparedStatement.setString(3, distributor.getCountry());

            preparedStatement.execute();

            return distributor;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the distributor: " + distributor);
        }
    }

    public List<Distributor> findAll() {

        List<Distributor> distributors = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from distributors";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                distributors.add(mapToDistributor(resultSet));
            }
            resultSet.close();

            return distributors;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all distributors!");
        }
    }

    private Distributor mapToDistributor(ResultSet resultSet) throws SQLException {
        Distributor distributor = new Distributor(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        return distributor;
    }

    public void update(String id, String name, String country) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryName = "{? = call change_distributor_name(?, ?)}",
                    queryCountry = "{? = call change_country(?, ?)}";

            if(name != null && !name.isEmpty()) {
                CallableStatement callableStatement = connection.prepareCall(queryName);
                callableStatement.setString(2, id);
                callableStatement.setString(3, name);
                callableStatement.executeUpdate();
            }

            if(country != null && !country.isEmpty()) {
                CallableStatement callableStatement = connection.prepareCall(queryCountry);
                callableStatement.setString(2, id);
                callableStatement.setString(3, country);
                callableStatement.executeUpdate();
            }

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to update the distributor with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from distributors where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the category with id: " + id);
        }
    }
}
