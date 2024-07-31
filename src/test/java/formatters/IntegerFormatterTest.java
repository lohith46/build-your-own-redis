package formatters;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.*;

class IntegerFormatterTest {

  private final IntegerFormatter formatter = new IntegerFormatter();

  public static Stream<Arguments> testData() {
    return Stream.of(
      Arguments.of(1),
      Arguments.of(1000),
      Arguments.of(-1)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testData")
  void shouldReturnDefaultStringIrrespectiveOfValuePassed(Integer number) {
    String formattedString = formatter.format(number);
    assertEquals( SIMPLE_INTEGER + number + CRLF, formattedString);
  }
}
