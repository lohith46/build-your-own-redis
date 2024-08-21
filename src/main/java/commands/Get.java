package commands;

import formatters.*;
import parser.*;

import java.io.*;
import java.util.*;

public class Get implements Command {

  private final InputReader inputReader = new InputReader();
  private final ValueFormatterContext context = new ValueFormatterContext();

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int noOfCommands) throws IOException {
    String key = readInput(bufferedReader);
    String value = store.get(key);
    printOutput(output, value);
  }

  String readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readBufferReader(bufferedReader);
  }

  void printOutput(PrintWriter output, String value) {
    String response = context.formatValue(Objects.nonNull(value) ? value : null );
    output.print(response);
    output.flush();
  }

}
