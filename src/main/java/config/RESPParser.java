package config;

import java.io.*;
import java.util.*;

public class RESPParser {

  public static List<String> parseRESP(BufferedReader bufferedReader) throws IOException {
    List<String> commandParts = new ArrayList<>();
    String line = bufferedReader.readLine();

    if (line == null || !line.startsWith("*")) {
      throw new IOException("Invalid RESP format");
    }

    int numberOfArguments = Integer.parseInt(line.substring(1));

    for (int i = 0; i < numberOfArguments; i++) {
      line = bufferedReader.readLine(); // Expecting $<length>
      if (line == null || !line.startsWith("$")) {
        throw new IOException("Invalid RESP format");
      }

      int length = Integer.parseInt(line.substring(1)); // Length of the next argument

      line = bufferedReader.readLine(); // The actual argument
      if (line == null || line.length() != length) {
        throw new IOException("Invalid RESP format or argument length mismatch");
      }

      commandParts.add(line);
    }

    return commandParts;
  }
}
