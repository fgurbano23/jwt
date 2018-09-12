package com.example.demo.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class Authentication {

  @Autowired
  private  DataSource dataSource;

  public Authentication(){}

  public  String userSearcher(String user, String password){
    return "SELECT * FROM USR_ACCOUNT WHERE ACCT_EMAIL ='"+user+"' ACCT_PASSWORD ='"+password+"'";
  }

  public  boolean isValid (String username, String password){
    System.out.println(username);
    boolean response= false;
    try (Connection connection = dataSource.getConnection();
         //Statement stmt = connection.createStatement();
         ){
      System.out.println("Conectado");
    } catch (java.lang.NullPointerException e) {System.out.println("aqui 1");}
    catch (java.sql.SQLException e) {System.out.println("auqi 2");}

//    try (
////      Connection connection = DriverManager.getConnection(
////        "jdbc:mysql://localhost:3306/mydb","furbano","fu082018");
//    ){
//      ResultSet result = stmt.executeQuery(userSearcher(username,password));
//      int rowCount = result.last() ? result.getRow() : 0;
//      System.out.println(rowCount);
//      if(rowCount > 0 ){ response=true;}
//
//    } catch (SQLException exc) {
//      exc.printStackTrace();
//    }

    // return (username.equals("admin") && password.equals("admin"));
    return response;
  }
}
