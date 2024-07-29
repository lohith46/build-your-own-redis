package commands;

import parser.*;
import response.*;

import java.io.*;
import java.util.*;

import static utils.Constants.NULL_STRING;

public class MGet extends Command<BufferedReader, PrintWriter, List<String>> {

  private final InputReader inputReader = new InputReader();
  private final RespCommand<List<String>> respCommand = new RespCommand<>();
  int iterateBufferReaderCount = 0;

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int iterateTill) throws IOException {
    iterateBufferReaderCount = iterateTill;
    List<String> keys = readInput(bufferedReader);
    List<String> values = fetchValues(store, keys);
    printOutput(output, values);
  }

  private List<String> fetchValues(Map<String, String> store, List<String> keys) {
    List<String> values = new ArrayList<>();
    for (String key : keys) {
      String value = store.get(key);
      values.add(Objects.nonNull(value) ? value : NULL_STRING);
    }
    return values;
  }

  @Override
  List<String> readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readNBufferReader(bufferedReader, getDefaultIterateTill());
  }

  private int getDefaultIterateTill() {
    return iterateBufferReaderCount;
  }

  @Override
  void printOutput(PrintWriter output, List<String> values) {
    String response = respCommand.respCommand(values);
    output.print(response);
    output.flush();
  }

}
