package commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.CRLF;

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
    String key = command.readInput(bufferedReader);

    assertEquals(expectedKey, key);
  }

  public static Stream<Arguments> testDataForInputReader() {
    return Stream.of(
      Arguments.of(new BufferedReader(new StringReader("$4" + CRLF + "key1" + CRLF)), "key1"),
      Arguments.of(new BufferedReader(new StringReader("$4" + CRLF + "key1" + CRLF)), "key1"),
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
      Arguments.of("-1", "+-1" + CRLF),
      Arguments.of("Hello, world!", "+Hello, world!" + CRLF),
      Arguments.of("Are you fine?", "+Are you fine?" + CRLF),
      Arguments.of("Redis", "+Redis" + CRLF),
      Arguments.of("Dynamo DB", "+Dynamo DB" + CRLF)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForExecuteMethod")
  void shouldBeAbleToExecuteTheGetCommand(String inputBufferStr, String expectedOutput) throws IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    command.execute(store, new BufferedReader(new StringReader(inputBufferStr)), printWriter, 1);
    printWriter.flush();

    assertEquals(expectedOutput, stringWriter.toString());

  }

  public static Stream<Arguments> testDataForExecuteMethod() {
    return Stream.of(
      Arguments.of("$4" + CRLF + "key1" + CRLF + "$6" + CRLF + " key100" + CRLF, "+value1" + CRLF),
      Arguments.of("$5" + CRLF + "key12" + CRLF, "$-1" + CRLF),
      Arguments.of("$4" + CRLF + "key2" + CRLF, "+value2" + CRLF),
      Arguments.of("$4" + CRLF + "key3" + CRLF, "+value3" + CRLF)
    );
  }
}
