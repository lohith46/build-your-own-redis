import lombok.extern.slf4j.*;
import recovery.*;
import utils.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static utils.Constants.*;

@Slf4j
public class RedisServer {
  public static void main(String[] args) {
    SnapshotScheduler scheduler = new SnapshotScheduler();
    Socket clientSocket = null;
    Map<String, String> store = new HashMap<>();
    int port = 6379;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      BufferedReader reader = new BufferedReader(new FileReader(AOF_FILE));

      AOFReplay.replay(reader, store, new PrintWriter(System.out, true));

      scheduler.scheduleDatabaseSnapshot(store);

      while (true) {
        clientSocket = serverSocket.accept();
        log.info("New client connected");
        new ClientHandler(clientSocket, store).start();
      }
    } catch (IOException ex) {
      log.error("Server exception: {}", ex.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        log.error("IOException: {}", e.getMessage());
      }
    }
  }
}

