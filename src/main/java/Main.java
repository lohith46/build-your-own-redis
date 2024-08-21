import config.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    Socket clientSocket = null;
    Map<String, String> store = new HashMap<>();
    int port = 6379;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server is listening on port " + port);

      AOFReplayer.replay("appendonly.aof", store, new PrintWriter(System.out, true));

      while (true) {
        clientSocket = serverSocket.accept();
        System.out.println("New client connected");
        new ClientHandler(clientSocket, store).start();
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

