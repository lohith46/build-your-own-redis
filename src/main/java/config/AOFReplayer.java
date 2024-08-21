package config;

import commands.*;

import java.io.*;
import java.util.*;

public class AOFReplayer {
  public static void replayAOF(String aofFileName, Map<String, String> store, PrintWriter output) throws IOException {
    CommandRegistry commandRegistry = new CommandRegistry();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(aofFileName));
    String input = bufferedReader.readLine();
    System.out.println(input);
    String convertToRESP = RESPConverter.convertToRESP(input);
    StringReader commandString = new StringReader(convertToRESP);
    BufferedReader commandReader = new BufferedReader(commandString);
    List<String> commandParts;
    while ((commandParts = RESPParser.parseRESP(commandReader)) != null) {
      String commandName = commandParts.get(0).toUpperCase();
      Command command = commandRegistry.getCommand(commandName);

      if (command != null) {
        // Create a BufferedReader for the parsed command parts
        StringReader commandString1 = new StringReader(String.join(" ", commandParts));
        BufferedReader commandReader1 = new BufferedReader(commandString1);

        // Execute the command, passing the aofLogger to log to AOF if necessary
        command.execute(store, commandReader1, output, commandParts.size());
      } else {
        output.println("Unknown command: " + commandName);
      }
    }

    bufferedReader.close();
  }
}

