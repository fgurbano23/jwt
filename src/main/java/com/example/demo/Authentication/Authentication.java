/*package com.example.demo.Authentication;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Authentication {

  public Authentication(){}

  public static String userSearcher(String user, String password){
    return "SELECT * FROM USR_ACCOUNT WHERE ACCT_USERNAME_ID ='"+user+"' AND ACCT_PASSWORD ='"+password+"'";
  }

  public static boolean isValid (String username, String password){
    boolean response= false;
    int rowCount = 0;
    try (
      Connection connection = DriverManager.getConnection(
      "jdbc:oracle:thin:@192.168.7.196:1521:TVTD","TVT","TVTd738");

            Statement stmt = connection.createStatement();
    ){
      ResultSet result = stmt.executeQuery(userSearcher(username,password));

      while(result.next()){
       rowCount++;
      }System.out.println(rowCount);
      if(rowCount > 0){
        response=true;
      }
   } catch (SQLException exc) {
     exc.printStackTrace();
   }

    // return (username.equals("admin") && password.equals("admin"));
    return response;
  }
}*/

