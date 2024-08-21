package recovery;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.CRLF;
import static utils.Constants.OK_RESPONSE;

class AOFReplayTest {

  Map<String, String> store = new HashMap<>();

  @Test
  void shouldBeAbleToReplayCommandsFromFile() throws IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    List<StringReader> readers = new ArrayList<>();
    readers.add(new StringReader("SET foo bar"));
    readers.add(new StringReader("SET key value"));
    readers.add(new StringReader("SET hello world"));

    for(StringReader reader : readers) {
      BufferedReader bufferedReader = new BufferedReader(reader);
      AOFReplay.replay(bufferedReader, store, printWriter);
    }


    printWriter.close();
    String output = stringWriter.toString();

    assertTrue(output.contains(OK_RESPONSE + CRLF));
    assertTrue(store.containsKey("foo"));
    assertTrue(store.containsValue("bar"));
    assertTrue(store.containsKey("key"));
    assertTrue(store.containsValue("value"));
    assertTrue(store.containsKey("hello"));
    assertTrue(store.containsValue("world"));
    assertFalse(store.containsKey("foo1"));
  }
}
