package com.atmachine;

import java.net.http.HttpClient;

public class App {

  private static final TodoList TODOLIST = new TodoList(HttpClient.newHttpClient());

  public static void main(String[] args) {
    var retval = TODOLIST.getItem("test");
    System.out.println(retval);
  }
}
