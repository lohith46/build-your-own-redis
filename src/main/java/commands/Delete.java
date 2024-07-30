package commands;

import formatters.*;
import parser.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class Delete extends Command<BufferedReader, PrintWriter, List<String>, Integer> {

  private final InputReader inputReader = new InputReader();
  private final ValueFormatterContext context = new ValueFormatterContext();

  private final int iterateBufferReaderCount;

  public Delete(int iterateBufferReaderCount) {
    this.iterateBufferReaderCount = iterateBufferReaderCount;
  }

  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter printWriter) throws IOException {
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

  @Override
  List<String> readInput(BufferedReader bufferedReader) throws IOException {
    return inputReader.readNBufferReader(bufferedReader, getDefaultIterateTill());
  }

  private int getDefaultIterateTill() {
    return iterateBufferReaderCount;
  }

  @Override
  void printOutput(PrintWriter output, Integer count) {
    String response = context.formatValue(count);
    output.print(response);
    output.flush();
  }
}
