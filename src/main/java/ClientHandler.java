import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler extends Thread {
  private static final String PING_COMMAND = "PING";
  private static final String SET_COMMAND = "SET";
  private static final String GET_COMMAND = "GET";
  private final Socket socket;
  private static Map<String, String> store = new HashMap<>();

  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try (InputStream input = socket.getInputStream();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
         PrintWriter output = new PrintWriter(socket.getOutputStream(), true);) {

      handleClient(bufferedReader, output);
    } catch (IOException ex) {
      System.out.println("Server exception: " + ex.getMessage());
    }
  }

  private static void handleClient(BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String content;
    while ((content = bufferedReader.readLine()) != null) {
      System.out.println("Command: " +content);
      switch (content.toUpperCase()) {
        case PING_COMMAND: //*1$4PING
          handlePingCommand(output);
          break;
        case SET_COMMAND: //*3$3SET
          handleSetCommand(bufferedReader, output);
          break;
        case GET_COMMAND: //*2$3GET
          handleGetCommand(bufferedReader, output);
          break;
      }
    }
  }

  private static void handlePingCommand(PrintWriter output) {
    output.print("+PONG\r\n");
    output.flush();
  }

  private static void handleSetCommand(BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String key = readKeyOrValue(bufferedReader);
    String value = readKeyOrValue(bufferedReader);
    store.put(key, value);
    output.print("+OK\r\n");
    output.flush();
  }

  private static void handleGetCommand(BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String key = readKeyOrValue(bufferedReader);
    String value = store.get(key);
    output.print(getCommand(value));
    output.flush();
  }

  private static String readKeyOrValue(BufferedReader bufferedReader) throws IOException {
    bufferedReader.readLine(); // Read the length line and ignore it
    return bufferedReader.readLine(); // Read the actual key or value
  }

  private static String getCommand(String value) {
    if (value != null) {
      return "+" + value + "\r\n";
    } else {
      return "$-1\r\n"; // Redis convention for null bulk strings
    }
  }
}
