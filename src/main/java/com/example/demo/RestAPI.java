package com.example.demo;

import ch.qos.logback.core.subst.Token;
// import com.example.demo.Authentication.Authentication;
import com.example.demo.models.Account;
import com.example.demo.models.ModuleConfiguration;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SignatureSpi;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import org.json.*;
@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
public class RestAPI {
/*

    @Autowired
    Authentication auth;
*/

/*    @RequestMapping( value = "auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
  public Object response(@RequestBody  Account account) {
      // System.out.println( account.getUsername() + account.getPassword());
      boolean status = auth.isValid(account.getUsername(), account.getPassword());
      if (status) {
          String KEY = "mySecretKey";
          byte[] apiKey = Base64.getEncoder().encode(KEY.getBytes()); // no es realmente necesario
          long time = System.currentTimeMillis();
          String jwt = Jwts.builder()
                  .signWith(SignatureAlgorithm.HS256, apiKey)
                  .setSubject(account.getUsername())
                  .setIssuedAt(new Date(time))
                  .setExpiration(new Date(time + 60000))
                  .claim("email", "email@yahoo.com")
                  .claim("AtributoCustom", "It's work")
                  .compact();
          //JsonObject json = Json.createObjectBuilder().add("JWT", jwt).build();
          // si se agrega json a entity se complica la respuesta JWT
          return Response.status(Response.Status.CREATED).entity(jwt).build();
      }
      return Response.status(Response.Status.UNAUTHORIZED).build();
  }
}*/

@RequestMapping( value = "auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    public Object response(@RequestBody  Account account) {
        ArrayList<ModuleConfiguration> moduleConfiguration = new ArrayList<ModuleConfiguration>();
        String sql = "SELECT" +
                " SM.SYS_MOD_NAME, USP.USR_PRIVILEGE_ID" +
                " FROM USR_ACCOUNT UA" +
                " JOIN USR_USER UU" +
                " ON UA.ACCT_USR_ID = UU.USR_ID" +
                " JOIN USR_PROFILE UP" +
                " ON UP.USR_PROFILE_ID = UU.USR_USER_PROFILE_ID" +
                " JOIN USR_SYS_MODULE USM" +
                " ON UP.USR_PROFILE_ID = USM.SYS_USR_PROFILE_ID" +
                " JOIN SYS_MODULE SM" +
                " ON USM.SYS_MODULE_ID = SM.SYS_MODULE_ID" +
                " JOIN USER_PRIVILEGE USP" +
                " ON USM.USR_PRIVILEGE_ID = USP.USR_PRIVILEGE_ID" +
                " WHERE UA.ACCT_USERNAME_ID='"
                + account.getUsername()+
                "' AND UA.ACCT_PASSWORD= '"
                + account.getPassword()+"'";

        System.out.println(sql);
        int rowCount = 0;
        try (
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:oracle:thin:@192.168.7.196:1521:TVTD","TVT","TVTd738"
                    );
            Statement stmt = connection.createStatement();
        ){
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                rowCount++;
                moduleConfiguration.add(
                        new ModuleConfiguration(
                                resultSet.getString("SYS_MOD_NAME"),
                                resultSet.getString("USR_PRIVILEGE_ID")
                        )
                );
            }
            if(rowCount > 0)
            {
                String KEY = "mySecretKey";
                byte[] apiKey = Base64.getEncoder().encode(KEY.getBytes()); // no es realmente necesario
                long time = System.currentTimeMillis();
                String jwt = Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, apiKey)
                        .setSubject(account.getUsername())
                        .setIssuedAt(new Date(time))
                        .setExpiration(new Date(time + 60000))
                        .claim("profile",moduleConfiguration)
                        .compact();
                return Response.status(Response.Status.CREATED).entity(jwt).build();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}

