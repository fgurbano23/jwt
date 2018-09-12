package com.example.demo;

import ch.qos.logback.core.subst.Token;
import com.example.demo.Authentication.Authentication;
import com.example.demo.models.Account;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SignatureSpi;
import java.util.Base64;
import java.util.Date;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RestController
public class RestAPI {

  @RequestMapping ( value = "auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
  public Object response(@RequestBody  Account account) {
      // System.out.println( account.getUsername() + account.getPassword());
      Authentication auth = new Authentication();
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
}

