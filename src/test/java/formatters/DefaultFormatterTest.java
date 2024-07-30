package formatters;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.*;

class DefaultFormatterTest {

  DefaultFormatter formatter = new DefaultFormatter();

  public static Stream<Arguments> testData() {
    return Stream.of(
      Arguments.of(1),
      Arguments.of(1000),
      Arguments.of(-1)
    );
  }

  @Test
  void shouldReturnDefaultStringIrrespectiveOfValuePassed() {
    String formattedString = formatter.format("1");
    assertEquals(BULK_STRINGS + -1 + CRLF, formattedString);
  }
}
