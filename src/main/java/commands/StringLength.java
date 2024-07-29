package commands;

import formatters.*;
import parser.*;

import java.io.*;
import java.util.*;

public class StringLength extends Command<BufferedReader, PrintWriter, String> {

  public static final Integer DEFAULT_RESPONSE = 0;
  ValueFormatterContext context = new ValueFormatterContext();
  private final InputReader inputReader = new InputReader();

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String input = readInput(bufferedReader);
    String storedValue = store.get(input);
    printOutput(output, storedValue);
  }

  @Override
  String readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readBufferReader(bufferedReader);
  }

  @Override
  void printOutput(PrintWriter output, String value) {
    String response = context.formatValue(Objects.nonNull(value) ? value.length() : DEFAULT_RESPONSE);
    output.print(response);
    output.flush();
  }

}
