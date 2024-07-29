package formatters;

import static utils.Constants.*;

public class DefaultFormatter implements ValueFormatter {

  @Override
  public String format(Object value) {
    return BULK_STRINGS + -1 + CRLF;
  }
}
