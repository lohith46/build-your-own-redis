package commands;

import java.io.*;

public abstract class Command<T, R> {
  abstract String readInput(T bufferedReader) throws IOException;
  abstract void printOutput(R output, String value) throws IOException;
}
