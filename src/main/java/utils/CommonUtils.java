package utils;

import static utils.Constants.ARRAY;

public class CommonUtils {

  public static int fetchNumberOfCommands(String content, int numberOfCommands) {
    if (content.startsWith(ARRAY)) {
      numberOfCommands = Integer.parseInt(content.substring(1,2));
    }
    return numberOfCommands;
  }
}
