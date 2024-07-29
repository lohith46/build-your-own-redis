import commands.Set;
import commands.*;

import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler extends Thread {
  private static final String PING_COMMAND = "PING";
  private static final String SET_COMMAND = "SET";
  private static final String GET_COMMAND = "GET";
  private static final String STRING_LENGTH_COMMAND = "STRLEN";
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
          new Ping().execute(output);
          break;
        case SET_COMMAND: //*3$3SET
          new Set().execute(store, bufferedReader, output);
          break;
        case GET_COMMAND: //*2$3GET
          new Get().execute(store, bufferedReader, output);
          break;
        case STRING_LENGTH_COMMAND: //*2$6STRLEN
          new StringLength().execute(store, bufferedReader, output);
          break;
      }
    }
  }

  private static void handlePingCommand(PrintWriter output) {
    output.print("+PONG\r\n");
    output.flush();
  }
}
