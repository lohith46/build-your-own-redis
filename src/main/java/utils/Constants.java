package utils;

public class Constants {
  public static final String SIMPLE_INTEGER = ":";
  public static final String BULK_STRINGS = "$";
  public static final String SIMPLE_STRING = "+";
  public static final String CRLF = "\r\n";
  public static final String ARRAY = "*";
  public static final String PING_COMMAND = "PING";
  public static final String SET_COMMAND = "SET";
  public static final String GET_COMMAND = "GET";
  public static final String MGET_COMMAND = "MGET";
  public static final String DEL_COMMAND = "DEL";
  public static final String SAVE_COMMAND = "SAVE";
  public static final String STRING_LENGTH_COMMAND = "STRLEN";
  public static final String NULL_STRING = "-1";
  public static final String OK_RESPONSE = "+OK";
  public static final String DEFAULT_RESPONSE = "+PONG\r\n";
  public static final String AOF_FILE = "appendonly.aof";
  public static final String SNAPSHOT_PREFIX = "snapshot";
  public static final String SNAPSHOT_RDB_FORMAT = ".rdb";
  public static final int SNAPSHOT_SCHEDULER_IN_HOURS = 1;
}
