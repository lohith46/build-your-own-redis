package parser;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class InputReader {

  public String readBufferReader(BufferedReader bufferedReader) throws IOException {
    bufferedReader.readLine(); // Read the length line and ignore it
    return bufferedReader.readLine(); // Read the actual key or value
  }

  public List<String> readNBufferReader(BufferedReader bufferedReader, int iterateTill) throws IOException {
    List<String> keys = new ArrayList<>();
    IntStream.range(0, iterateTill).forEach(i -> {
      try {
        bufferedReader.readLine();
        keys.add(bufferedReader.readLine());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    return keys;
  }
}
