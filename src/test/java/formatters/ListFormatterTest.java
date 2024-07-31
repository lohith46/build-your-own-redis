package formatters;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.*;

class ListFormatterTest {

  private final ListFormatter formatter = new ListFormatter();

  public static Stream<Arguments> testData() {
    return Stream.of(
      Arguments.of(new ArrayList<>(List.of()), ARRAY + 0 + CRLF),
      Arguments.of(new ArrayList<>(List.of("key")), ARRAY + 1 + CRLF + BULK_STRINGS + 3 + CRLF + "key" + CRLF),
      Arguments.of(new ArrayList<>(List.of("key", "abcd", "-1")), ARRAY + 3 + CRLF + BULK_STRINGS + 3 + CRLF + "key" + CRLF + BULK_STRINGS + 4 + CRLF + "abcd" + CRLF + BULK_STRINGS + -1 + CRLF),
      Arguments.of(new ArrayList<>(List.of("")), ARRAY + 1 + CRLF + BULK_STRINGS + 0 + CRLF + "" + CRLF)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testData")
  void shouldPickCorrectFormatterForGivenObjectValueAndFormatAndReturnBackString(List<String> values, String expected) {
    String actual = formatter.format(values);
    assertEquals(expected, actual);
  }
}
