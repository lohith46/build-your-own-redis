package persistence;

import lombok.extern.slf4j.*;

import java.io.*;

import static utils.Constants.AOF_FILE;

@Slf4j
public class AOFLogger {
  private static BufferedWriter writer;

  static {
    try {
      writer = new BufferedWriter(new FileWriter(AOF_FILE, true));
    } catch (IOException e) {
      log.error("Exception while creating file {}", e.getMessage());
    }
  }

  public static synchronized void log(String command) {
    try {
      writer.write(command);
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      log.error("Exception while logging the command to AOF file {}", e.getMessage());
    }
  }

  public static void close() throws IOException {
    if (writer != null) {
      writer.close();
    }
  }
}
