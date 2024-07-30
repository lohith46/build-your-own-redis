package commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

class GetTest {
  private Get command;
  private Map<String, String> store = new HashMap<>();

  @BeforeEach
  void setUp() {
    command = new Get();
    store = new HashMap<>(Map.of("key1", "value1", "key2", "value2", "key3", "value3"));
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForInputReader")
  void shouldBeAbleToReadInputAndReturnValueForAGivenKey(BufferedReader bufferedReader, String expectedKey) throws IOException {
    command = new Get();

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
      Arguments.of("-1", "+-1\r\n"),
      Arguments.of("Hello, world!", "+Hello, world!\r\n"),
      Arguments.of("Are you fine?", "+Are you fine?\r\n"),
      Arguments.of("Redis", "+Redis\r\n"),
      Arguments.of("Dynamo DB", "+Dynamo DB\r\n")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForExecuteMethod")
  void shouldBeAbleToExecuteTheGetCommand(String inputBufferStr, String expectedOutput) throws IOException {
    command = new Get();
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    command.execute(store, new BufferedReader(new StringReader(inputBufferStr)), printWriter);
    printWriter.flush();

    assertEquals(expectedOutput, stringWriter.toString());

  }

  public static Stream<Arguments> testDataForExecuteMethod() {
    return Stream.of(
      Arguments.of("$4\r\nkey1\r\n$6\r\nkey100\r\n", "+value1\r\n"),
      Arguments.of("$5\r\nkey12\r\n", "$-1\r\n"),
      Arguments.of("$4\r\nkey2\r\n", "+value2\r\n"),
      Arguments.of("$4\r\nkey3\r\n", "+value3\r\n")
    );
  }
}
