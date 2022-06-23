package com.atmachine;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class TodoList {
  private final HttpClient httpClient;

  public TodoList(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public String getItem(String item) {

    String responseBody = null;

    try {
      var request =
          HttpRequest.newBuilder()
              .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
              .header("accept", "application/json")
              .GET()
              .build();

      var response = httpClient.send(request, BodyHandlers.ofString());

      responseBody = response.body();
      var statusCode = response.statusCode();

      System.out.println("Status Code: " + statusCode);

      if (statusCode < 200 || statusCode > 299) {
        if (statusCode == 500) {
          throw new WebException("500 thing not found");
        }
        if (statusCode == 404) {
          throw new WebException("404 thing not found");
        }
        throw new WebException("web exception thrown");
      }

    } catch (IOException e) {
      System.out.println("IO exception occurred");
    } catch (InterruptedException e) {
      System.out.println("Interrupted exception occurred");
    }

    return responseBody;
  }
}
