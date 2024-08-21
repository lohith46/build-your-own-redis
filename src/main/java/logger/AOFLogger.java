package logger;

import java.io.*;

public class AOFLogger {
  private static final String AOF_FILE = "appendonly.aof";
  private static BufferedWriter writer;
  private static boolean isLoading = false;

  static {
    try {
      writer = new BufferedWriter(new FileWriter(AOF_FILE, true));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void log(String command) {
    if (isLoading) {
      return; // Do not log if in loading mode
    }
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

  public static void setLoading(boolean loading) {
    isLoading = loading;
  }
}
