package commands;

import java.io.*;

public abstract class Command<T, R, U> {
  abstract U readInput(T bufferedReader) throws IOException;
  abstract void printOutput(R output, U value) throws IOException;
}
