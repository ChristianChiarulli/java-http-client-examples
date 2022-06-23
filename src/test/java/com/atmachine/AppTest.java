package com.atmachine;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppTest {

  @InjectMocks TodoList todoList;

  @Mock HttpClient httpClient;

  @Mock HttpResponse<String> httpResponse;

  @Test
  public void throw404() {

    todoList = new TodoList(httpClient);

    WebException thrown =
        Assertions.assertThrows(
            WebException.class,
            () -> {
              String item = "item";
              var request =
                  HttpRequest.newBuilder()
                      .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                      .header("accept", "application/json")
                      .GET()
                      .build();
              Mockito.when(httpResponse.statusCode()).thenReturn(404);
              try {
                Mockito.when(httpClient.send(request, BodyHandlers.ofString()))
                    .thenReturn(httpResponse);
              } catch (IOException e) {
                System.out.println("IO exception occurred");
              } catch (InterruptedException e) {
                System.out.println("Interrupted exception occurred");
              }

              todoList.getItem(item);
            });
    Assert.assertEquals("404 thing not found", thrown.getMessage());
  }
}
