package commands;

import logger.*;
import parser.*;

import java.io.*;
import java.util.*;

import static utils.Constants.*;

public class Set implements Command{

  private final InputReader inputReader = new InputReader();

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int noOfCommands, boolean updateLogFile) throws IOException {
    String key = readInput(bufferedReader);
    String value = readInput(bufferedReader);

    store.put(key, value);

    // Log the SET command to the AOF file
    if(updateLogFile) {
      String commandLog = String.format("SET %s %s", key, value);
      AOFLogger.log(commandLog);
    }

    printOutput(output, OK_RESPONSE+CRLF);
  }

  String readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readBufferReader(bufferedReader);
  }

  void printOutput(PrintWriter output, String value) {
    output.print(value);
    output.flush();
  }

}
