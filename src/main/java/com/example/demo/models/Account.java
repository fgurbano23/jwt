package com.example.demo.models;

public class Account {
  public String username;
  public String password;

  public Account(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Account(){}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
