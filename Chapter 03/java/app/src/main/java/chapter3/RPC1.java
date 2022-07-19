package chapter3;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.net.http.HttpResponse.*;
import java.time.Duration;

public class RPC1 {
  public static void main(String[] args) {
    HttpClient client = HttpClient.newBuilder()
        .version(Version.HTTP_1_1)
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(20))
        //.proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
        //.authenticator(Authenticator.getDefault())
        .build();
   //HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
   //System.out.println(response.statusCode());
   //System.out.println(response.body());

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://gorest.co.in/public/v1/users"))
        .timeout(Duration.ofMinutes(2))
        .header("Content-Type", "application/json")
        //.POST(BodyPublishers.ofByteArray(buf)))
        .GET()
        .build();

  HttpResponse<String> response = null;
  try {
    response = client.send(request, BodyHandlers.ofString());
  } catch (IOException | InterruptedException e) {
    e.printStackTrace();
  }
  System.out.println(response.statusCode());
  System.out.println(response.body());

  // client.sendAsync(request, BodyHandlers.ofString())
  //      .thenApply(HttpResponse::body)
  //      .thenAccept(System.out::println);
  }
}
