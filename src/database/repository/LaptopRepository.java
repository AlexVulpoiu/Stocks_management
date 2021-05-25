package database.repository;

import database.config.DatabaseConfiguration;
import stocks_management.category.Category;
import stocks_management.distributor.Distributor;
import stocks_management.product.Laptop;
import stocks_management.services.StockService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaptopRepository {

    public Laptop save(Laptop laptop) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "insert into laptops(id, name, category_id, distributor_id, price, stock, warranty, diagonal, cpu, ram, memory, storage_type, graphics_card, usb_ports, use_category)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, laptop.getProductId());
            preparedStatement.setString(2, laptop.getProductName());
            preparedStatement.setString(3, laptop.getProductCategory().getCategoryId());
            preparedStatement.setString(4, laptop.getProductDistributor().getDistributorId());
            preparedStatement.setDouble(5, laptop.getPrice());
            preparedStatement.setInt(6, laptop.getStock());
            preparedStatement.setInt(7, laptop.getWarranty());
            preparedStatement.setDouble(8, laptop.getDiagonal());
            preparedStatement.setString(9, laptop.getCpu());
            preparedStatement.setInt(10, laptop.getRam());
            preparedStatement.setInt(11, laptop.getMemory());
            preparedStatement.setString(12, laptop.getStorageType());
            preparedStatement.setString(13, laptop.getGraphicsCard());
            preparedStatement.setInt(14, laptop.getUsbPorts());
            preparedStatement.setString(15, laptop.getCategory());

            preparedStatement.execute();

            return laptop;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the laptop: " + laptop);
        }
    }

    public List<Laptop> findAll() {

        List<Laptop> laptops = new ArrayList<>();
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "select * from laptops";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                laptops.add(mapToLaptop(resultSet));
            }
            resultSet.close();

            return laptops;

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all laptops!");
        }
    }

    private Laptop mapToLaptop(ResultSet resultSet) throws SQLException {

        StockService stockService = StockService.getInstance();
        String categoryId = resultSet.getString(3),
                distributorId = resultSet.getString(4);
        Category category = stockService.findCategoryById(categoryId);
        Distributor distributor = stockService.findDistributorById(distributorId);

        Laptop laptop = new Laptop(resultSet.getString(1),
                                    resultSet.getInt(6),
                                    resultSet.getString(2),
                                    category, distributor,
                                    resultSet.getInt(5),
                                    resultSet.getInt(7),
                                    resultSet.getDouble(8),
                                    resultSet.getString(9),
                                    resultSet.getInt(10),
                                    resultSet.getInt(11),
                                    resultSet.getString(12),
                                    resultSet.getString(13),
                                    resultSet.getInt(14));

        return laptop;
    }

    public void update(String id, Double price, Integer stock) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String queryPrice = "{? = call change_laptop_price(?, ?)}",
                    queryStock = "{? = call change_laptop_stock(?, ?)}";

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
            throw new RuntimeException("Something went wrong while trying to update the laptop with id: " + id);
        }
    }

    public void delete(String id) {

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "delete from laptops where id = '" + id + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch(SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to delete the laptop with id: " + id);
        }
    }

}
