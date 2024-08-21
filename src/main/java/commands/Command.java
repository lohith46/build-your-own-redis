package commands;

import java.io.*;
import java.util.*;

public interface Command {
  void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int noOfCommands) throws IOException;
}
