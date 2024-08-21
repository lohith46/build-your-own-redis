package commands;

import java.io.*;
import java.util.*;

import static utils.Constants.DEFAULT_RESPONSE;

public class Ping implements Command {

  @Override
  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int noOfCommands) throws IOException {
    printOutput(output, DEFAULT_RESPONSE);
  }

  String readInput(BufferedReader bufferedReader) {
    return null;
  }

  void printOutput(PrintWriter output, String value) {
    output.print(value);
    output.flush();
  }
}
