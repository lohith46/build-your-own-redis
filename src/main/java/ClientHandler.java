import commands.Set;
import commands.*;

import java.io.*;
import java.net.*;
import java.util.*;

import static utils.Constants.*;

class ClientHandler extends Thread {
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
    int numberOfCommands = 0;
    int count = 0;
    while ((content = bufferedReader.readLine()) != null && count <= numberOfCommands) {
      count++;
      System.out.println("Command: " +content);
      numberOfCommands = fetchNumberOfCommands(content, numberOfCommands);
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
        case MGET_COMMAND: //*2$6STRLEN
          new MGet().execute(store, bufferedReader, output, numberOfCommands-1);
          //output.print("*2\r\n$5\r\nhello\r\n$5\r\nworld\r\n");
          break;
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
