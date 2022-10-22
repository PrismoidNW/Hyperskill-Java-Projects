package carsharing.dao.h2;

import carsharing.dao.BaseDao;
import carsharing.dao.CarDao;
import carsharing.entitie.Car;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CarDaoH2Impl extends BaseDao implements CarDao {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS CAR" +
            "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NAME VARCHAR(255) NOT NULL UNIQUE, " +
            "COMPANY_ID INT NOT NULL, " +
            "CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID)" +
            "REFERENCES COMPANY(ID))";
    private static final String GET_CARS = "SELECT * FROM CAR WHERE COMPANY_ID = ?";
    private static final String CREATE_CAR = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES(?, ?)";
    private static final String GET_CAR = "SELECT * FROM CAR WHERE ID = ?";
    private static final String GET_CAR_BY_NAME = "SELECT * FROM CAR WHERE NAME = ?";

    public CarDaoH2Impl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getCreateTableSQL() {
        return CREATE_TABLE_SQL;
    }

    @Override
    public List<Car> getCars(int companyId) {
        List<Car> companies = new LinkedList<>();
        try(PreparedStatement stmt = connection.prepareStatement(GET_CARS)) {
            stmt.setString(1, String.valueOf(companyId));
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                companies.add(new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void createCar(String car, int companyId) {

        try(PreparedStatement stmt = connection.prepareStatement(CREATE_CAR)) {
            stmt.setString(1, car);
            stmt.setString(2, String.valueOf(companyId));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Car getCar(int carId) {
        try(PreparedStatement stmt = connection.prepareStatement(GET_CAR)) {
            stmt.setString(1, String.valueOf(carId));
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
               Car car = new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID"));
                return car;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
