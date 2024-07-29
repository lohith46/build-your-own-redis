package commands;

import parser.*;

import java.io.*;
import java.util.*;

public class Set extends Command<BufferedReader, PrintWriter> {

  private static final String OK_RESPONSE = "+OK\r\n";
  private final InputReader inputReader = new InputReader();

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String key = readInput(bufferedReader);
    String value = readInput(bufferedReader);

    store.put(key, value);

    printOutput(output, OK_RESPONSE);
  }

  @Override
  String readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readBufferReader(bufferedReader);
  }

  @Override
  void printOutput(PrintWriter output, String value) {
    output.print(value);
    output.flush();
  }

}