package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.AudioSpeaker;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AudioSpeakerRepository {

    public AudioSpeaker save(AudioSpeaker audioSpeaker) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into audio_speakers(id, name, category_id, distributor_id, price, stock, warranty, power, is_wireless, has_bluetooth)\n"
                    + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, audioSpeaker.getProductId());
            preparedStatement.setString(2, audioSpeaker.getProductName());
            preparedStatement.setString(3, audioSpeaker.getProductCategory().getCategoryId());
            preparedStatement.setString(4, audioSpeaker.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, audioSpeaker.getPrice());
            preparedStatement.setInt(6, audioSpeaker.getStock());
            preparedStatement.setInt(7, audioSpeaker.getWarranty());
            preparedStatement.setInt(8, audioSpeaker.getPower());
            preparedStatement.setBoolean(9, audioSpeaker.isWireless());
            preparedStatement.setBoolean(10, audioSpeaker.hasBluetooth());

            preparedStatement.execute();

            return audioSpeaker;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the audio speaker: " + audioSpeaker);
        }
    }

    public List<AudioSpeaker> findAll() {

        List<AudioSpeaker> audioSpeakers = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from audio_speakers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                audioSpeakers.add(mapToAudioSpeaker(resultSet));
            }
            resultSet.close();

            return audioSpeakers;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all audio speakers!");
        }
    }

    private AudioSpeaker mapToAudioSpeaker(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        AudioSpeaker audioSpeaker = new AudioSpeaker(resultSet.getString(1),
                                                        resultSet.getInt(6),
                                                        resultSet.getString(2),
                                                        category, distributor,
                                                        resultSet.getDouble(5),
                                                        resultSet.getInt(7),
                                                        resultSet.getInt(8),
                                                        resultSet.getBoolean(9),
                                                        resultSet.getBoolean(10));

        return audioSpeaker;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_audio_speaker_price(?, ?)}",
                    queryStock = "{? = call change_audio_speaker_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the audio speaker with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from audio_speakers where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the audio speaker with id: " + id);
        }
    }
}
