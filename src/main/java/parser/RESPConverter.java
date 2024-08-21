package parser;

import static utils.Constants.CRLF;

public class RESPConverter {

  public static String convertToRESP(String command) {
    // Split the command into parts
    String[] parts = command.split(" ");

    // Create a StringBuilder to build the RESP formatted string
    StringBuilder respBuilder = new StringBuilder();

    // RESP format starts with the array size
    respBuilder.append("*").append(parts.length).append(CRLF);

    // Process each part
    for (String part : parts) {
      // Add the length of the part
      respBuilder.append("$").append(part.length()).append(CRLF);
      // Add the actual part
      respBuilder.append(part).append(CRLF);
    }

    return respBuilder.toString();
  }
}
