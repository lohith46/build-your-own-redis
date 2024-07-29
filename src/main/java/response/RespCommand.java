package response;

import formatters.*;

public class RespCommand<T> {
  ValueFormatterContext context = new ValueFormatterContext();

  public String respCommand(T value) {
    return context.formatValue(value);
  }
}
