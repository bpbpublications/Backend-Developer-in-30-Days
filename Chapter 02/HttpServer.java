import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class HttpServer {
  final private static String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
  final private static String DYNAMIC = "/dynamic.html";
  final private static int PORT = 8080;
  final private static byte[] NOT_FOUND_HTML = "<h1>Not found :(</h1>".getBytes();
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
              try {
                Socket client = serverSocket.accept();
                    handleClient(client);
                }
                catch(Exception err) {
                  err.printStackTrace();
                }
            }
        }
    }

    private static void handleClient(Socket client) throws Exception {
      // Uncomment the next line to introduce a 10 seconds delay in each request
      Thread.sleep(3000);
      System.out.println(" Current time in seconds: "+ Instant.now().getEpochSecond());

      // Get the input stream
      BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

      List<String> requestsLines = new ArrayList<>();
      String line;

      // Read all lines in the request until you find a blank line
      do {
        line = br.readLine();
        requestsLines.add(line);
      } while(line != null && !line.isBlank());

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
        output.write(("HTTP/1.1 " + status).getBytes());
        output.write(("ContentType: " + contentType + LINE_BREAK).getBytes());
        output.write(LINE_BREAK.getBytes());
        output.write(content);
        output.write((LINE_BREAK + LINE_BREAK).getBytes());
        output.flush();
        client.close();
    }
}