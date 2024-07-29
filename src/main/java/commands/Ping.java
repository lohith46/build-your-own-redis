package commands;

import java.io.*;

public class Ping extends Command<BufferedReader, PrintWriter> {

  public static final String DEFAULT_RESPONSE = "+PONG\r\n";

  public void execute(PrintWriter output) {
    printOutput(output, DEFAULT_RESPONSE);
  }

  @Override
  String readInput(BufferedReader bufferedReader) {
    return null;
  }

  @Override
  void printOutput(PrintWriter output, String value) {
    output.print(value);
    output.flush();
  }
}
