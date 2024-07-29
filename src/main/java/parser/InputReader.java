package parser;

import java.io.*;

public class InputReader {

  public String readBufferReader(BufferedReader bufferedReader) throws IOException {
    bufferedReader.readLine(); // Read the length line and ignore it
    return bufferedReader.readLine(); // Read the actual key or value
  }
}
