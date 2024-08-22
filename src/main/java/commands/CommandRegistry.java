package commands;

import java.util.*;

import static utils.Constants.DEL_COMMAND;
import static utils.Constants.GET_COMMAND;
import static utils.Constants.MGET_COMMAND;
import static utils.Constants.PING_COMMAND;
import static utils.Constants.SAVE_COMMAND;
import static utils.Constants.SET_COMMAND;
import static utils.Constants.STRING_LENGTH_COMMAND;

public class CommandRegistry {

  private final Map<String, Command> commandMap = new HashMap<>();

  public CommandRegistry() {
    commandMap.put(PING_COMMAND, new Ping());
    commandMap.put(SET_COMMAND, new Set());
    commandMap.put(GET_COMMAND, new Get());
    commandMap.put(STRING_LENGTH_COMMAND, new StringLength());
    commandMap.put(MGET_COMMAND, new MGet());
    commandMap.put(DEL_COMMAND, new Delete());
    commandMap.put(SAVE_COMMAND, new Snapshot());
  }

  public Command getCommand(String commandName) {
    return commandMap.get(commandName.toUpperCase());
  }
}
