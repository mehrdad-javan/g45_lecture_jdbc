package se.lexicon;

import se.lexicon.model.Person;
import se.lexicon.model.Task;

import java.sql.*;

public class JDBCDemo {

  public static void main(String[] args) {

    try {
      // define database connection
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lecture_db", "root", "root");

      Statement statement = connection.createStatement();

      //String selectAllTasks = "select * from task";
      //String selectAllTasks = "select id, title, _description as description, person_id from task";
      String selectAllTasks = "select t.id, t.title, t._description as description, t.person_id, p.* from task t left join person p on t.person_id = p.id;";

      // executeQuery() is used to execute SELECT query
      ResultSet resultSet = statement.executeQuery(selectAllTasks);

      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String desc = resultSet.getString("description");
        int personId = resultSet.getInt("person_id");
        Task task = new Task(id, title, desc);


        String firstName = resultSet.getString("p.first_name");
        String lastName = resultSet.getString("p.last_name");
        Date regDate = resultSet.getDate("p.reg_date");
        boolean active = resultSet.getBoolean("p._active");


        if(personId != 0){
          Person person = new Person(personId, firstName, lastName, regDate.toLocalDate(), active );
          task.setPerson(person);
        }

        //System.out.println("Task Id: " + id + " Title: " + title + " Description: " + desc + " PersonId: " + personId + " Name: " + firstName + " " + lastName);
        System.out.println(task);

      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

}
