package se.lexicon;

import se.lexicon.exception.DBConnectionException;

import java.sql.*;
import java.time.LocalDate;

public class JDBCExamples {

  public static void main(String[] args) {
    ex5();
  }


  public static void ex1() {
    String query = "select * from task";

    try (
            Connection connection = MySqlConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
    ) {


      while (resultSet.next()) {
        System.out.println(resultSet.getInt("id"));
        System.out.println(resultSet.getString("title"));
        System.out.println(resultSet.getString("_description"));
        System.out.println("-------------");
      }

    } catch (DBConnectionException | SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  public static void ex2() {

    String query = "select * from task where id = ? and title like ?";

    int taskId = 3;
    try (
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

    ) {

      preparedStatement.setInt(1, taskId);
      preparedStatement.setString(2, "t" + "%");

      try (ResultSet resultSet = preparedStatement.executeQuery();) {

        while (resultSet.next()) {
          System.out.println(resultSet.getInt("id"));
          System.out.println(resultSet.getString("title"));
          System.out.println(resultSet.getString("_description"));
          System.out.println("-------------");
        }

      }

    } catch (DBConnectionException | SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  public static void ex3() {
    int taskId = 5;
    String query = "delete from task where id = ?";

    try (
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
    ) {

      preparedStatement.setInt(1, taskId);

      int rowAffected = preparedStatement.executeUpdate();
      System.out.println(rowAffected);

    } catch (DBConnectionException | SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

  public static void ex4() {
    String query = "insert into task(title, _description) values (?, ?)";

    try (
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    ) {

      preparedStatement.setString(1, "TEST1");
      preparedStatement.setString(2, "DESC1");

      int result = preparedStatement.executeUpdate();
      System.out.println(result);

      try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {

        if (resultSet.next()) {
          System.out.println("Task ID Is: " + resultSet.getInt(1));
        }

      }


    } catch (DBConnectionException | SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static void ex5() {

    String queryAddress = "insert into address (city, zip_code) values(?, ?)";
    String queryPerson = "insert into person (first_name, last_name, email, birth_date, address_id) values (?,?,?,?,?)";

    try (
            Connection connection = MySqlConnection.getConnection();
            PreparedStatement preparedStatementAddress = connection.prepareStatement(queryAddress, Statement.RETURN_GENERATED_KEYS);
    ) {

      connection.setAutoCommit(false);

      preparedStatementAddress.setString(1, "TEST");
      preparedStatementAddress.setString(2, "12345");

      preparedStatementAddress.executeUpdate();
      Integer addressId = null;
      try (ResultSet resultSet = preparedStatementAddress.getGeneratedKeys();) {
        if (resultSet.next()) {
          addressId = resultSet.getInt(1);
        }
      }

      if (addressId == null) {
        throw new RuntimeException("getGeneratedKeys value for address query was null");
      }


      try (
              PreparedStatement preparedStatementPerson = connection.prepareStatement(queryPerson, Statement.RETURN_GENERATED_KEYS);
      ) {

        preparedStatementPerson.setString(1, "TEST");
        preparedStatementPerson.setString(2, "TEST");
        preparedStatementPerson.setString(3, "TEST.TEST@TEST.TEST");
        preparedStatementPerson.setDate(4, Date.valueOf(LocalDate.parse("2000-01-01")));
        preparedStatementPerson.setInt(5, addressId);

        preparedStatementPerson.executeUpdate();

        try (ResultSet resultSet = preparedStatementPerson.getGeneratedKeys();) {
          if (resultSet.next()) {
            System.out.println("Person ID: " + resultSet.getInt(1));
          }
        }
      }

      connection.commit();

    } catch (DBConnectionException | SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }


  }

}
