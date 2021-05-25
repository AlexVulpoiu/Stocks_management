package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.AudioSystem;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AudioSystemRepository {

    public AudioSystem save(AudioSystem audioSystem) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into audio_systems(id, name, category_id, distributor_id, price, stock, warranty, power, pieces, is_wireless, has_bluetooth)\n"
                    + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, audioSystem.getProductId());
            preparedStatement.setString(2, audioSystem.getProductName());
            preparedStatement.setString(3, audioSystem.getProductCategory().getCategoryId());
            preparedStatement.setString(4, audioSystem.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, audioSystem.getPrice());
            preparedStatement.setInt(6, audioSystem.getStock());
            preparedStatement.setInt(7, audioSystem.getWarranty());
            preparedStatement.setInt(8, audioSystem.getPower());
            preparedStatement.setInt(9, audioSystem.getNumberOfPieces());
            preparedStatement.setBoolean(10, audioSystem.isWireless());
            preparedStatement.setBoolean(11, audioSystem.hasBluetooth());

            preparedStatement.execute();

            return audioSystem;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the audio system: " + audioSystem);
        }
    }

    public List<AudioSystem> findAll() {

        List<AudioSystem> audioSystems = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from audio_systems";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                audioSystems.add(mapToAudioSystem(resultSet));
            }
            resultSet.close();

            return audioSystems;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all audio systems!");
        }
    }

    private AudioSystem mapToAudioSystem(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        AudioSystem audioSystem = new AudioSystem(resultSet.getString(1),
                                                    resultSet.getInt(6),
                                                    resultSet.getString(2),
                                                    category, distributor,
                                                    resultSet.getDouble(5),
                                                    resultSet.getInt(7),
                                                    resultSet.getInt(8),
                                                    resultSet.getInt(9),
                                                    resultSet.getBoolean(10),
                                                    resultSet.getBoolean(11));

        return audioSystem;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_audio_system_price(?, ?)}",
                    queryStock = "{? = call change_audio_system_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the audio system with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from audio_systems where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the audio system with id: " + id);
        }
    }
}
