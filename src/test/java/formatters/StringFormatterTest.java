package formatters;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.*;

class StringFormatterTest {

  StringFormatter formatter = new StringFormatter();

  public static Stream<Arguments> testData() {
    return Stream.of(
      Arguments.of("a"),
      Arguments.of("Hello World!"),
      Arguments.of("How are you doing??")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testData")
  void shouldReturnDefaultStringIrrespectiveOfValuePassed(String value) {
    String formattedString = formatter.format(value);
    assertEquals( SIMPLE_STRING + value + CRLF, formattedString);
  }

  @Test
  void shouldReturnNullStringIfGivenStringIsNullOrEmpty() {
    String formattedString = formatter.format("");
    assertEquals( BULK_STRINGS + -1 + CRLF, formattedString);
  }
}
