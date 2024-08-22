package commands;

import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

import static utils.Constants.CRLF;
import static utils.Constants.OK_RESPONSE;
import static utils.Constants.SNAPSHOT_FILE;

public class Snapshot implements Command{

  Logger logger = LoggerFactory.getLogger(Snapshot.class);
  private final Lock lock = new ReentrantLock();

  @Override
  public void execute(Map<String, String> store, BufferedReader bufferedReader, PrintWriter output, int noOfCommands, boolean updateLogFile) throws IOException {
    lock.lock();  // Lock the database to prevent writes
    try (FileOutputStream fos = new FileOutputStream(SNAPSHOT_FILE, true);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

      oos.writeObject(store);  // Serialize the in-memory database to file
      oos.flush();  // Ensure all data is written

      logger.info("Database saved successfully.");
      output.print(OK_RESPONSE+CRLF);
      output.flush();
    } catch (IOException e) {
      logger.error("Error saving database: {}", e.getMessage());
    } finally {
      lock.unlock();  // Unlock the database
    }
  }
}
