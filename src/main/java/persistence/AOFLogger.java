package persistence;

import java.io.*;

import static utils.Constants.AOF_FILE;

public class AOFLogger {
  private static BufferedWriter writer;

  static {
    try {
      writer = new BufferedWriter(new FileWriter(AOF_FILE, true));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void log(String command) {
    try {
      writer.write(command);
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void close() throws IOException {
    if (writer != null) {
      writer.close();
    }
  }
}
