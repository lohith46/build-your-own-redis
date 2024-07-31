package commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.CRLF;
import static utils.Constants.SIMPLE_INTEGER;

class StringLengthTest {

  StringLength command;
  private Map<String, String> store = new HashMap<>();

  @BeforeEach
  void setUp() {
    command = new StringLength();
    store = new HashMap<>(Map.of("key1", "value1", "key2", "value2", "key3", "value3"));
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForInputReader")
  void shouldBeAbleToReadInputAndReturnValueForAGivenKey(BufferedReader bufferedReader, String expectedKey) throws IOException {
    String key = command.readInput(bufferedReader);

    assertEquals(expectedKey, key);
  }

  public static Stream<Arguments> testDataForInputReader() {
    return Stream.of(
      Arguments.of(new BufferedReader(new StringReader("$4\r\nkey1\r\n")), "key1"),
      Arguments.of(new BufferedReader(new StringReader("$4\r\nkey1\r\n")), "key1"),
      Arguments.of(new BufferedReader(new StringReader("")), null)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForPrintOutput")
  void shouldBeAbleToPrintOutputValueForAGivenKey(String value , String expectedOutput) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    command.printOutput(printWriter, value);
    printWriter.flush();

    assertEquals(expectedOutput, stringWriter.toString());
  }

  public static Stream<Arguments> testDataForPrintOutput() {
    return Stream.of(
      Arguments.of("-1", SIMPLE_INTEGER + 2 + CRLF),
      Arguments.of("Hello, world!", SIMPLE_INTEGER + 13 + CRLF),
      Arguments.of("Are you fine?", SIMPLE_INTEGER + 13 + CRLF),
      Arguments.of("Redis", SIMPLE_INTEGER + 5 + CRLF),
      Arguments.of("Dynamo DB", SIMPLE_INTEGER + 9 + CRLF),
      Arguments.of("", SIMPLE_INTEGER + 0 + CRLF),
      Arguments.of(null, SIMPLE_INTEGER + 0 + CRLF)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForExecuteMethod")
  void shouldBeAbleToExecuteTheStringLengthCommand(String inputBufferStr, String expectedOutput) throws IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    command.execute(store, new BufferedReader(new StringReader(inputBufferStr)), printWriter);
    printWriter.flush();

    assertEquals(expectedOutput, stringWriter.toString());

  }

  public static Stream<Arguments> testDataForExecuteMethod() {
    return Stream.of(
      Arguments.of("$4\r\nkey1\r\n$6\r\nkey100\r\n", SIMPLE_INTEGER + 6 + CRLF),
      Arguments.of("$5\r\nkey12\r\n", SIMPLE_INTEGER + 0 + CRLF),
      Arguments.of("$4\r\nkey2\r\n", SIMPLE_INTEGER + 6 + CRLF),
      Arguments.of("$4\r\nkey3\r\n", SIMPLE_INTEGER + 6 + CRLF)
    );
  }
}
