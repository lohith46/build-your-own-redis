package commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.BULK_STRINGS;
import static utils.Constants.CRLF;
import static utils.Constants.OK_RESPONSE;

class SetTest {

  private Set command;
  private Map<String, String> store = new HashMap<>();

  @BeforeEach
  void setUp() {
    command = new Set();
    store = new HashMap<>();
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForInputReader")
  void shouldBeAbleToReadInputAndReturnValueForAGivenKey(BufferedReader bufferedReader, String expectedKey) throws IOException {
    String key = command.readInput(bufferedReader);

    assertEquals(expectedKey, key);
  }

  public static Stream<Arguments> testDataForInputReader() {
    return Stream.of(
      Arguments.of(new BufferedReader(new StringReader(BULK_STRINGS + "4" + CRLF + "key1" + CRLF)), "key1"),
      Arguments.of(new BufferedReader(new StringReader(BULK_STRINGS + "4" + CRLF + "key1" + CRLF)), "key1"),
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
      Arguments.of("-1", "-1"),
      Arguments.of("Hello, world!", "Hello, world!"),
      Arguments.of("Are you fine?", "Are you fine?"),
      Arguments.of("Redis", "Redis"),
      Arguments.of("Dynamo DB", "Dynamo DB")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForExecuteMethod")
  void shouldBeAbleToExecuteTheSetCommand(String inputBufferStr, String key, String value) throws IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    command.execute(store, new BufferedReader(new StringReader(inputBufferStr)), printWriter);
    printWriter.flush();
    assertEquals(OK_RESPONSE, stringWriter.toString());
    assertEquals(store.get(key), value);
  }

  public static Stream<Arguments> testDataForExecuteMethod() {
    return Stream.of(
      Arguments.of(BULK_STRINGS + "4" + CRLF + "key1" + CRLF + BULK_STRINGS + "6" + CRLF + "value1" + CRLF, "key1", "value1"),
      Arguments.of(BULK_STRINGS + "5" + CRLF + "key12" + CRLF +  BULK_STRINGS + "7" + CRLF + "value12" + CRLF, "key12", "value12"),
      Arguments.of(BULK_STRINGS + "4" + CRLF + "name" + CRLF +  BULK_STRINGS + "8" + CRLF + "full name" + CRLF, "name", "full name")
    );
  }

}
