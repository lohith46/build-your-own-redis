package recovery;

import commands.*;
import org.slf4j.*;
import parser.*;

import java.io.*;
import java.util.*;

import static utils.Constants.*;

public class AOFReplayer {

  static Logger logger = LoggerFactory.getLogger(AOFReplayer.class);

  public static void replay(String fileName, Map<String, String> store, PrintWriter output) throws IOException {
    String command;
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    while((command = reader.readLine()) != null) {
      String convertToRESP = RESPConverter.convertToRESP(command);
      StringReader commandString = new StringReader(convertToRESP);
      BufferedReader bufferedReader = new BufferedReader(commandString);
      executeCommand(store, output, bufferedReader);
    }
  }

  private static void executeCommand(Map<String, String> store, PrintWriter output, BufferedReader bufferedReader) throws IOException {
    String content;
    CommandRegistry commandRegistry = new CommandRegistry();
    int numberOfCommands = 0;
    int count = 0;
    while ((content = bufferedReader.readLine()) != null && count <= numberOfCommands) {
      count++;
      logger.info("Command: {}",content);
      numberOfCommands = fetchNumberOfCommands(content, numberOfCommands);
      Command command = commandRegistry.getCommand(content);
      if(command != null) {
        command.execute(store, bufferedReader, output, numberOfCommands, false);
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

