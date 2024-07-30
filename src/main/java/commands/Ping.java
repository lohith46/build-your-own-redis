package commands;

import java.io.*;

import static utils.Constants.*;

public class Ping extends Command<BufferedReader, PrintWriter, String, String> {

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
