package commands;

import java.io.*;

public abstract class Command<T, R, U, O> {
  abstract U readInput(T bufferedReader) throws IOException;
  abstract void printOutput(R output, O value) throws IOException;
}
