package formatters;

import static utils.Constants.*;

public class StringFormatter implements ValueFormatter {

  @Override
  public String format(Object value) {
    String stringValue = (String) value;
    if (stringValue.isEmpty()) {
      return BULK_STRINGS + -1 + CRLF;
    }
    return SIMPLE_STRING + stringValue + CRLF;
  }
}


