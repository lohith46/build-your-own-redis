import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
    Map<String, String> store = new ConcurrentHashMap<String, String>();

    Socket clientSocket = null;
    int port = 6379;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server is listening on port " + port);

      while (true) {
        clientSocket = serverSocket.accept();
        System.out.println("New client connected");
        new ClientHandler(clientSocket).start();
      }
    } catch (IOException ex) {
      System.out.println("Server exception: " + ex.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }
}

