package commands;

import parser.*;
import response.*;

import java.io.*;
import java.util.*;

public class MGet extends Command<BufferedReader, PrintWriter> {

  private final InputReader inputReader = new InputReader();
  private final RespCommand<List<String>> respCommand = new RespCommand<>();

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int iterateTill) throws IOException {
    List<String> keys = inputReader.readNBufferReader(bufferedReader, iterateTill);
    List<String> values = new ArrayList<>();
    for (String key : keys) {
      String value = store.get(key);
      values.add(Objects.nonNull(value) ? value : "-1");
    }
    String response = respCommand.respCommand(values);
    output.print(response);
    output.flush();
  }

  @Override
  String readInput(BufferedReader bufferedReader) {
    return null;
  }

  @Override
  void printOutput(PrintWriter output, String value) {

  }

}
