package commands;

import formatters.*;
import parser.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class Delete implements Command {

  private final InputReader inputReader = new InputReader();
  private final ValueFormatterContext context = new ValueFormatterContext();

  public void setIterateBufferReaderCount(int iterateBufferReaderCount) {
    this.iterateBufferReaderCount = iterateBufferReaderCount;
  }

  private int iterateBufferReaderCount;

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter printWriter, int noOfCommands) throws IOException {
    iterateBufferReaderCount = noOfCommands;
    List<String> keys = readInput(bufferedReader);
    int count = removeKeys(store, keys);
    printOutput(printWriter, count);
  }

  private int removeKeys(Map<String, String> store, List<String> keys) {
    AtomicInteger count = new AtomicInteger(0);
    keys.removeIf(key -> {
      if (store.containsKey(key)) {
        count.incrementAndGet();
        store.remove(key);
        return true;
      }
      return false;
    });
    return count.get();
  }

  List<String> readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readNBufferReader(bufferedReader, getDefaultIterateTill());
  }

  private int getDefaultIterateTill() {
    return iterateBufferReaderCount;
  }

  void printOutput(PrintWriter output, Integer count) {
    String response = context.formatValue(count);
    output.print(response);
    output.flush();
  }
}
