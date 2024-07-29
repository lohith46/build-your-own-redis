package formatters;

import static utils.Constants.*;

public class IntegerFormatter implements ValueFormatter {

  @Override
  public String format(Object value) {
    return SIMPLE_INTEGER + value + CRLF;
  }
}

