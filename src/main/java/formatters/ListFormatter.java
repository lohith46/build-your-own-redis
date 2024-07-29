package formatters;

import java.util.*;

import static utils.Constants.*;

public class ListFormatter implements ValueFormatter {

  @Override
  public String format(Object value) {
    List<?> valueList = (List<?>) value;
    StringBuilder stringBuilder = new StringBuilder()
      .append(ARRAY)
      .append(valueList.size())
      .append(CRLF);

    for (Object v : valueList) {
      String stringValue = (v == null) ? NULL_STRING : v.toString();

      if (NULL_STRING.equals(stringValue)) {
        stringBuilder.append(BULK_STRINGS)
          .append(-1)
          .append(CRLF);
      } else {
        stringBuilder.append(BULK_STRINGS)
          .append(stringValue.length())
          .append(CRLF)
          .append(stringValue)
          .append(CRLF);
      }
    }
    return stringBuilder.toString();
  }
}

