package formatters;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.*;

class ValueFormatterContextTest<T> {

  private ValueFormatterContext valueFormatterContext = new ValueFormatterContext();

  public static Stream<Arguments> testDataToVerifyVariousFormatters() {
    return Stream.of(
      Arguments.of("abc", SIMPLE_STRING + "abc" + CRLF),
      Arguments.of(1, SIMPLE_INTEGER + 1 + CRLF),
      Arguments.of("", BULK_STRINGS + -1 + CRLF),
      Arguments.of(new ArrayList<>(List.of()), ARRAY + 0 + CRLF),
      Arguments.of(new ArrayList<>(List.of("key")), ARRAY + 1 + CRLF + BULK_STRINGS + 3 + CRLF + "key" + CRLF),
      Arguments.of(new ArrayList<>(List.of("key", "abcd", "-1")), ARRAY + 3 + CRLF + BULK_STRINGS + 3 + CRLF + "key" + CRLF + BULK_STRINGS + 4 + CRLF + "abcd" + CRLF + BULK_STRINGS + -1 + CRLF),
      Arguments.of(new ArrayList<>(List.of("")), ARRAY + 1 + CRLF + BULK_STRINGS + 0 + CRLF + "" + CRLF)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataToVerifyVariousFormatters")
  void shouldPickCorrectFormatterForGivenObjectValueAndFormatAndReturnBackString(T value, String expected) {
    String actual = valueFormatterContext.formatValue(value);
    assertEquals(expected, actual);
  }
}
