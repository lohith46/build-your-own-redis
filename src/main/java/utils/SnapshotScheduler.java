package utils;

import commands.*;
import lombok.extern.slf4j.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import static utils.Constants.SNAPSHOT_SCHEDULER_IN_HOURS;

@Slf4j
public class SnapshotScheduler {
  Snapshot snapshot = new Snapshot();

  public void scheduleDatabaseSnapshot(Map<String, String> store) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    // Schedule the task to run every hour, with an initial delay of 0 seconds
    scheduler.scheduleAtFixedRate(() -> {
      try {
        snapshot.execute(store);
      } catch (IOException e) {
        log.error("Exception while trying to take snapshot {}", e.getMessage());
        throw new RuntimeException(e);
      }
    }, 0, SNAPSHOT_SCHEDULER_IN_HOURS, TimeUnit.HOURS);
  }
}
