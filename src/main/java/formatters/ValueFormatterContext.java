package formatters;

import java.util.*;

public class ValueFormatterContext {
  private final Map<Class<?>, ValueFormatter> formatterMap = new HashMap<>();

  public ValueFormatterContext() {
    formatterMap.put(Integer.class, new IntegerFormatter());
    formatterMap.put(String.class, new StringFormatter());
    formatterMap.put(ArrayList.class, new ListFormatter());
  }

  public String formatValue(Object value) {
    ValueFormatter formatter = new DefaultFormatter();
    if(Objects.nonNull(value))
      formatter = formatterMap.get(value.getClass());
    return formatter.format(value);
  }
}

