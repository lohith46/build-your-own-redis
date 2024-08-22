import commands.*;
import lombok.extern.slf4j.*;
import org.slf4j.*;
import utils.*;

import java.io.*;
import java.net.*;
import java.util.*;

@Slf4j
class ClientHandler extends Thread {
  private final Socket socket;
  private static Map<String, String> store = new HashMap<>();

  public ClientHandler(Socket socket, Map<String, String> store) {
    this.socket = socket;
    this.store = store;
  }

  public void run() {
    try (InputStream input = socket.getInputStream();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
         PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

      handleClient(bufferedReader, output);
    } catch (IOException ex) {
      log.error("Server exception: {}", ex.getMessage());
    }
  }

  private static void handleClient(BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String content;
    CommandRegistry commandRegistry = new CommandRegistry();
    int numberOfCommands = 0;
    int count = 0;
    while ((content = bufferedReader.readLine()) != null && count <= numberOfCommands) {
      count++;
      log.info("Command: {}",content);
      numberOfCommands = CommonUtils.fetchNumberOfCommands(content, numberOfCommands);
      Command command = commandRegistry.getCommand(content);
      if(command != null) {
        command.execute(store, bufferedReader, output, numberOfCommands, true);
      }
    }
  }
}
