import org.slf4j.*;
import recovery.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static utils.Constants.*;

public class RedisServer {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(RedisServer.class);
    Socket clientSocket = null;
    Map<String, String> store = new HashMap<>();
    int port = 6379;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Server is listening on port {}", port);

      AOFReplayer.replay(AOF_FILE, store, new PrintWriter(System.out, true));

      while (true) {
        clientSocket = serverSocket.accept();
        logger.info("New client connected");
        new ClientHandler(clientSocket, store).start();
      }
    } catch (IOException ex) {
      logger.error("Server exception: {}", ex.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        logger.error("IOException: {}", e.getMessage());
      }
    }
  }
}

