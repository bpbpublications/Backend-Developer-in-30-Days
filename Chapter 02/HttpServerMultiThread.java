import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class HttpServerMultiThread {
  static ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

  final private static int PORT = 8080;
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try {
                  Socket client = serverSocket.accept();
                  // new Thread(new ServerHandler(client)).start();
                  executorService.submit(new ServerHandler(client));
                }
                catch(Exception err) {
                  err.printStackTrace();
                }
            }
        }
    }
}

class ServerHandler implements Runnable {
  final private static String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
  final private static String DYNAMIC = "/dynamic.html";
  final private static byte[] NOT_FOUND_HTML = "<h1>Not found :(</h1>".getBytes();

  private Socket client;

  public ServerHandler(Socket client) {
    this.client = client;
  }

  public void run() {
    try {
      Thread.sleep(10000);
      System.out.println(" Current time in seconds: "+ Instant.now().getEpochSecond());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Get the input stream
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

      List<String> requestsLines = new ArrayList<>();
      String line;

      // Read all lines in the request until you find a blank line
      do {
        line = br.readLine();
        requestsLines.add(line);
      } while(!line.isBlank());

      // Parse the requested path from first line in the request
      String[] requestLine = requestsLines.get(0).split(" ");
      String path = requestLine[1];

      Path filePath = Paths.get(".", path);

      // if the path requested is dynamic.html, print today's day
      if(DYNAMIC.equals(path)) {
        sendResponse(client, "200 OK",
            "text/html",
            getDynamicResponse()
          );
      }
      // else, print static file
      else if (Files.exists(filePath)) {
          sendResponse(client, "200 OK",
            Files.probeContentType(filePath),
            Files.readAllBytes(filePath)
          );
      // else, print error message
      } else {
          sendResponse(client, "404 Not Found",
            "text/html",
            NOT_FOUND_HTML
          );
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private static byte[] getDynamicResponse() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    String response = String.format("<h1>Dynamic response</h1> Today is %s", sdf.format(cal.getTime()));

    return response.getBytes();
  }

  private static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException {
      String LINE_BREAK = "\r\n";
      OutputStream output = client.getOutputStream();
      output.write(("HTTP/1.1 " + LINE_BREAK + status).getBytes());
      output.write(("ContentType: " + contentType + LINE_BREAK).getBytes());
      output.write(LINE_BREAK.getBytes());
      output.write(content);
      output.write((LINE_BREAK + LINE_BREAK).getBytes());
      output.flush();
      client.close();
  }
}