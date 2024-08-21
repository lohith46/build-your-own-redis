package commands;

import java.util.*;

public class CommandRegistry {

  private final Map<String, Command> commandMap = new HashMap<>();

  public CommandRegistry() {
    commandMap.put("PING", new Ping());
    commandMap.put("SET", new Set());
    commandMap.put("GET", new Get());
    commandMap.put("STRLEN", new StringLength());
    commandMap.put("MGET", new MGet());
    commandMap.put("DEL", new Delete());
  }

  public Command getCommand(String commandName) {
    return commandMap.get(commandName.toUpperCase());
  }
}
