package commands;

import lombok.extern.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

import static utils.CommonUtils.generateDynamicFileName;
import static utils.Constants.*;

@Slf4j
public class Snapshot {
  private final Lock lock = new ReentrantLock();

  public void execute(Map<String, String> store) throws IOException {
    String fileName = generateDynamicFileName(SNAPSHOT_PREFIX, SNAPSHOT_RDB_FORMAT);
    lock.lock();  // Lock the database to prevent writes
    try (FileOutputStream fos = new FileOutputStream(fileName, true);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {

      oos.writeObject(store);  // Serialize the in-memory database to file
      oos.flush();  // Ensure all data is written

      log.info("Database saved successfully.");
    } catch (IOException e) {
      log.error("Error saving database: {}", e.getMessage());
    } finally {
      lock.unlock();  // Unlock the database
    }
  }
}
