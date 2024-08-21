package commands;

import java.io.*;
import java.util.*;

import static utils.Constants.DEFAULT_RESPONSE;

public class Ping implements Command {

  @Override
  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int noOfCommands, boolean updateLogFile) throws IOException {
    printOutput(output, DEFAULT_RESPONSE);
  }

  void printOutput(PrintWriter output, String value) {
    output.print(value);
    output.flush();
  }
}
