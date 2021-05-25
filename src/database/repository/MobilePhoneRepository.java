package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.MobilePhone;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MobilePhoneRepository {

    public MobilePhone save(MobilePhone mobilePhone) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into mobile_phones(id, name, category_id, distributor_id, price, stock, warranty, diagonal, ram, memory, cameras)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mobilePhone.getProductId());
            preparedStatement.setString(2, mobilePhone.getProductName());
            preparedStatement.setString(3, mobilePhone.getProductCategory().getCategoryId());
            preparedStatement.setString(4, mobilePhone.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, mobilePhone.getPrice());
            preparedStatement.setInt(6, mobilePhone.getStock());
            preparedStatement.setInt(7, mobilePhone.getWarranty());
            preparedStatement.setDouble(8, mobilePhone.getDiagonal());
            preparedStatement.setInt(9, mobilePhone.getRam());
            preparedStatement.setInt(10, mobilePhone.getMemory());
            preparedStatement.setInt(11, mobilePhone.getNumberOfCameras());

            preparedStatement.execute();

            return mobilePhone;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the mobile phone: " + mobilePhone);
        }
    }

    public List<MobilePhone> findAll() {

        List<MobilePhone> mobilePhones = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from mobile_phones";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                mobilePhones.add(mapToMobilePhone(resultSet));
            }
            resultSet.close();

            return mobilePhones;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all mobile phones!");
        }
    }

    private MobilePhone mapToMobilePhone(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        MobilePhone mobilePhone = new MobilePhone(resultSet.getString(1),
                                                    resultSet.getInt(6),
                                                    resultSet.getString(2),
                                                    category, distributor,
                                                    resultSet.getInt(5),
                                                    resultSet.getInt(7),
                                                    resultSet.getDouble(8),
                                                    resultSet.getInt(9),
                                                    resultSet.getInt(10),
                                                    resultSet.getInt(11));

        return mobilePhone;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_mobile_phone_price(?, ?)}",
                    queryStock = "{? = call change_mobile_phone_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the mobile phone with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from mobile_phones where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the mobile phone with id: " + id);
        }
    }
}
