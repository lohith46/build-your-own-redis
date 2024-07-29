package commands;

import parser.*;
import response.*;

import java.io.*;
import java.util.*;

public class Get extends Command<BufferedReader, PrintWriter> {

  private final InputReader inputReader = new InputReader();
  private final RespCommand<String> respCommand = new RespCommand<>();

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output) throws IOException {
    String key = readInput(bufferedReader);
    String value = store.get(key);
    printOutput(output, value);
  }

  @Override
  String readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readBufferReader(bufferedReader);
  }

  @Override
  void printOutput(PrintWriter output, String value) {
    String response = respCommand.respCommand(Objects.nonNull(value) ? value : null );
    output.print(response);
    output.flush();
  }

}
