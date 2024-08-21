package commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTest {

  private Delete delete;
  private Map<String, String> store = new HashMap<>();

  @BeforeEach
  void setUp() {
    delete = new Delete();
    store = new HashMap<>(Map.of("key1", "value1", "key2", "value2", "key3", "value3"));
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForInputReader")
  void shouldBeAbleToReadInputAndReturnListOfKeysToDelete(BufferedReader bufferedReader, List<String> expectedList, int noOfKeys) throws IOException {
    delete  = new Delete();
    delete.setIterateBufferReaderCount(noOfKeys);

    List<String> stringList = delete.readInput(bufferedReader);

    assertEquals(expectedList, stringList);
  }

//  @ParameterizedTest
//  @MethodSource(value = "testDataForPrintOutput")
//  void shouldBeAbleToPrintOutputForGivenValues(List<String> values , String expectedOutput) throws IOException {
//    StringWriter stringWriter = new StringWriter();
//    PrintWriter printWriter = new PrintWriter(stringWriter);
//
//    delete.printOutput(printWriter, values);
//    printWriter.flush();
//
//    assertEquals(expectedOutput, stringWriter.toString());
//  }

    @ParameterizedTest
  @MethodSource(value = "testDataForPrintOutput")
  void shouldBeAbleToPrintOutputForGivenValues(Integer count , String expectedOutput) throws IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    delete.printOutput(printWriter, count);
    printWriter.flush();

    assertEquals(expectedOutput, stringWriter.toString());
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForExecuteMethod")
  void shouldBeAbleToExecuteTheDeleteByRemovingKeysFromStore(String inputBufferStr, String expectedOutput,
                                                             List<String> removedKeys, int noOfKeys) throws IOException {
    delete  = new Delete();
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);

    delete.execute(store, new BufferedReader(new StringReader(inputBufferStr)), printWriter, noOfKeys, false);
    printWriter.flush();

    Map<String, String> updatedStore = store;
    removedKeys.forEach(key -> assertFalse(updatedStore.containsKey(key)));
    assertEquals(expectedOutput, stringWriter.toString());

  }

  public static Stream<Arguments> testDataForExecuteMethod() {
    return Stream.of(
      Arguments.of("$4\r\nkey1\r\n$6\r\nkey100\r\n", ":1\r\n", List.of("key1"), 1),
      Arguments.of("$5\r\nkey12\r\n$6\r\nkey100\r\n", ":0\r\n", List.of(), 2),
      Arguments.of("$4\r\nkey1\r\n$4\r\nkey2\r\n", ":2\r\n", List.of("key1", "key2"), 2),
      Arguments.of("$4\r\nkey1\r\n$4\r\nkey2\r\n$4\r\nkey3\r\n", ":3\r\n", List.of("key1", "key2", "key3"), 3)
    );
  }
  public static Stream<Arguments> testDataForInputReader() {
    return Stream.of(
      Arguments.of(new BufferedReader(new StringReader("$4\r\nkey1\r\n$4\r\nkey2\r\n$4\r\nkey3\r\n")), List.of("key1", "key2", "key3"), 3),
      Arguments.of(new BufferedReader(new StringReader("$4\r\nkey1\r\n")), List.of("key1"), 1),
      Arguments.of(new BufferedReader(new StringReader("")), List.of(), 0)
    );
  }

//  public static Stream<Arguments> testDataForPrintOutput() {
//    return Stream.of(
//      Arguments.of(new ArrayList<>(List.of("-1")), "*1\r\n$-1\r\n"),
//      Arguments.of(new ArrayList<>(List.of("Hello, world!")), "*1\r\n$13\r\nHello, world!\r\n"),
//      Arguments.of(new ArrayList<>(List.of("Hello, world!", "Are you fine?")), "*2\r\n$13\r\nHello, world!\r\n$13\r\nAre you fine?\r\n"),
//      Arguments.of(new ArrayList<>(List.of("Hello, world!", "Redis", "Dynamo DB")), "*3\r\n$13\r\nHello, world!\r\n$5\r\nRedis\r\n$9\r\nDynamo DB\r\n"),
//      Arguments.of(new ArrayList<>(List.of("Hello, world!", "Redis", "-1", "Dynamo DB")), "*4\r\n$13\r\nHello, world!\r\n$5\r\nRedis\r\n$-1\r\n$9\r\nDynamo DB\r\n")
//    );
//  }

  public static Stream<Arguments> testDataForPrintOutput() {
    return Stream.of(
      Arguments.of(0, ":0\r\n"),
      Arguments.of(1, ":1\r\n"),
      Arguments.of(2, ":2\r\n"),
      Arguments.of(3, ":3\r\n"),
      Arguments.of(4, ":4\r\n")
    );
  }
}
