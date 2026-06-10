package org.barahi.service.gamelogic;

import org.barahi.serviceapi.room.Room.RoomId;

import java.util.concurrent.*;

public class GameScheduler {
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
  private final ConcurrentHashMap<RoomId, ScheduledFuture<?>> activeTimers = new ConcurrentHashMap<>();

  public void startRoundTimer(RoomId roomId, int roundDuration, Runnable timeOverCallback){
    cancelTimer(roomId);
    ScheduledFuture<?> task = scheduler.schedule(() -> {
      try {
        timeOverCallback.run();
      } catch (Exception e){
        System.err.println("Error encountered running timer for room: " + roomId.getId().toString());
      } finally {
        activeTimers.remove(roomId);
      }
    }, roundDuration, TimeUnit.SECONDS);
    activeTimers.put(roomId, task);
  }

  public void cancelTimer(RoomId roomId){
    ScheduledFuture<?> activeTask = activeTimers.get(roomId);
    if (activeTask != null && !activeTask.isDone()){
      activeTask.cancel(true);
    }
  }
}
