package utils;

import java.time.*;
import java.time.format.*;

import static utils.Constants.ARRAY;

public class CommonUtils {

  public static int fetchNumberOfCommands(String content, int numberOfCommands) {
    if (content.startsWith(ARRAY)) {
      numberOfCommands = Integer.parseInt(content.substring(1,2));
    }
    return numberOfCommands;
  }

  public static String generateDynamicFileName(String prefix, String fileFormatType) {
    return  prefix + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + fileFormatType;
  }
}
