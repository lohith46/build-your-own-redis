package response;

import static utils.Constants.*;

public class RespCommand<T> {

  public String respCommand(T value) {
    if (value instanceof Integer) {
      return SIMPLE_INTEGER + value + CRLF;
    }
    if (value instanceof String) {
      if(!((String) value).isEmpty()){
        return SIMPLE_STRING + value + CRLF;
      }else {
        return BULK_STRINGS + -1 + CRLF;
      }
    }
    return BULK_STRINGS + -1 + CRLF;
  }
}
