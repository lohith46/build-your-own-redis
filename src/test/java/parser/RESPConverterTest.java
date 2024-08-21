package parser;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RESPConverterTest {

  public static Stream<Arguments> testDataForRedisCommandConversionToRespProtocol() {
    return Stream.of(
      Arguments.of("SET foo bar", "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"),
      Arguments.of("PING", "*1\r\n$4\r\nPING\r\n"),
      Arguments.of("GET foo", "*2\r\n$3\r\nGET\r\n$3\r\nfoo\r\n")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "testDataForRedisCommandConversionToRespProtocol")
  void shouldConvertGivenRedisCommandToRespProtocol(String input, String expected) {
    assertEquals(expected, RESPConverter.convertToRESP(input));
  }
}
