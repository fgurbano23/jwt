package com.example.demo.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Service
public class Auth {
    
    @Autowired
    private DataSource dataSource;
    
    public boolean isValid(String username, String password){
        boolean response= false;

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()
        ){
            System.out.println("Conectado");
            return true;
        } catch (java.lang.NullPointerException e) 
        {System.out.println("aqui 1");
            return false;}
        catch (java.sql.SQLException e) 
        {System.out.println("auqi 2");}


        return false;
    }
    
}
