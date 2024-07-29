package response;

import java.util.*;

public class RespCommand<T> {

  public String respCommand(T value) {
    if (value instanceof Integer) {
      return ":" + value + "\r\n";
    }
    if (value instanceof String) {
      if(!((String) value).isEmpty()){
        return "+" + value + "\r\n";
      }else {
        return "$" + -1 + "\r\n";
      }
    }
    return "$" + -1 + "\r\n";
  }
}
