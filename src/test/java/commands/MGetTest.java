package commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

class MGetTest {

  private MGet mGet;
  private Map<String, String> store = new HashMap<>();

  @BeforeEach
  void setUp() {
    mGet = new MGet();
    store = new HashMap<>(Map.of("key1", "value1", "key2", "value2", "key3", "value3"));
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForInputReader")
  void shouldBeAbleToReadInputAndReturnKeysValues(BufferedReader bufferedReader, List<String> expectedList, int noOfKeys) throws IOException {
    mGet = new MGet();
    mGet.setIterateBufferReaderCount(noOfKeys);

    List<String> stringList = mGet.readInput(bufferedReader);

    assertEquals(expectedList, stringList);
  }

  public static Stream<Arguments> testDataForInputReader() {
    return Stream.of(
      Arguments.of(new BufferedReader(new StringReader("$4\r\nkey1\r\n$4\r\nkey2\r\n$4\r\nkey3\r\n")), List.of("key1", "key2", "key3"), 3),
      Arguments.of(new BufferedReader(new StringReader("$4\r\nkey1\r\n")), List.of("key1"), 1),
      Arguments.of(new BufferedReader(new StringReader("")), List.of(), 0)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForPrintOutput")
  void shouldBeAbleToPrintOutputForGivenValues(List<String> values , String expectedOutput) throws IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    mGet.printOutput(printWriter, values);
    printWriter.flush();

    assertEquals(expectedOutput, stringWriter.toString());
  }

  public static Stream<Arguments> testDataForPrintOutput() {
    return Stream.of(
      Arguments.of(new ArrayList<>(List.of("-1")), "*1\r\n$-1\r\n"),
      Arguments.of(new ArrayList<>(List.of("Hello, world!")), "*1\r\n$13\r\nHello, world!\r\n"),
      Arguments.of(new ArrayList<>(List.of("Hello, world!", "Are you fine?")), "*2\r\n$13\r\nHello, world!\r\n$13\r\nAre you fine?\r\n"),
      Arguments.of(new ArrayList<>(List.of("Hello, world!", "Redis", "Dynamo DB")), "*3\r\n$13\r\nHello, world!\r\n$5\r\nRedis\r\n$9\r\nDynamo DB\r\n"),
      Arguments.of(new ArrayList<>(List.of("Hello, world!", "Redis", "-1", "Dynamo DB")), "*4\r\n$13\r\nHello, world!\r\n$5\r\nRedis\r\n$-1\r\n$9\r\nDynamo DB\r\n")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForExecuteMethod")
  void shouldBeAbleToExecuteTheMgetCommand(String inputBufferStr, String expectedOutput, int noOfKeys) throws IOException {
    mGet  = new MGet();
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    mGet.execute(store, new BufferedReader(new StringReader(inputBufferStr)), printWriter, noOfKeys);
    printWriter.flush();

    assertEquals(expectedOutput, stringWriter.toString());

  }

  public static Stream<Arguments> testDataForExecuteMethod() {
    return Stream.of(
      Arguments.of("$4\r\nkey1\r\n$6\r\nkey100\r\n", "*1\r\n$6\r\nvalue1\r\n", 1),
      Arguments.of("$5\r\nkey12\r\n$6\r\nkey100\r\n", "*2\r\n$-1\r\n$-1\r\n", 2),
      Arguments.of("$4\r\nkey1\r\n$4\r\nkey2\r\n", "*2\r\n$6\r\nvalue1\r\n$6\r\nvalue2\r\n", 2),
      Arguments.of("$4\r\nkey1\r\n$4\r\nkey2\r\n$4\r\nkey3\r\n", "*3\r\n$6\r\nvalue1\r\n$6\r\nvalue2\r\n$6\r\nvalue3\r\n" , 3)
    );
  }

}
