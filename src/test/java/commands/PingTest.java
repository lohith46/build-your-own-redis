package commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.SIMPLE_STRING;

class PingTest {
  private Ping command = new Ping();
  
  private Map<String, String> store = new HashMap<>();

  @BeforeEach
  void setUp() {
    command = new Ping();
    store = new HashMap<>(Map.of("key1", "value1", "key2", "value2", "key3", "value3"));
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

  @Test
  void shouldBeAbleToExecuteThePingCommand() throws IOException {
    command = new Ping();
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    command.execute(store, new BufferedReader(new StringReader("")), printWriter, 1, false);
    printWriter.flush();

    assertEquals(SIMPLE_STRING + "PONG\r\n", stringWriter.toString());
  }
}
