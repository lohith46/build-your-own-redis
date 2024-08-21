package recovery;

import commands.*;
import org.slf4j.*;
import parser.*;
import utils.*;

import java.io.*;
import java.util.*;

public class AOFReplay {

  static Logger logger = LoggerFactory.getLogger(AOFReplay.class);

  public static void replay(BufferedReader reader, Map<String, String> store, PrintWriter output) throws IOException {
    String command;
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
      numberOfCommands = CommonUtils.fetchNumberOfCommands(content, numberOfCommands);
      Command command = commandRegistry.getCommand(content);
      if(command != null) {
        command.execute(store, bufferedReader, output, numberOfCommands, false);
      }
    }
  }

}

