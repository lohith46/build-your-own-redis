import commands.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static utils.Constants.*;

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
      System.out.println("Server exception: " + ex.getMessage());
    }
  }

  private static void handleClient(BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String content;
    CommandRegistry commandRegistry = new CommandRegistry();
    int numberOfCommands = 0;
    int count = 0;
    while ((content = bufferedReader.readLine()) != null && count <= numberOfCommands) {
      count++;
      System.out.println("Command: " +content);
      numberOfCommands = fetchNumberOfCommands(content, numberOfCommands);
      Command command = commandRegistry.getCommand(content);
      if(command != null) {
        command.execute(store, bufferedReader, output, numberOfCommands);
      }
    }
  }

  private static int fetchNumberOfCommands(String content, int numberOfCommands) {
    if (content.startsWith(ARRAY)) {
      numberOfCommands = Integer.parseInt(content.substring(1,2));
    }
    return numberOfCommands;
  }
}
