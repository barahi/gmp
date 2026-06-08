package org.barahi.service.room;

import org.barahi.serviceapi.player.Player.PlayerId;
import org.barahi.serviceapi.room.Room.RoomId;

import java.util.List;

public class RoomDto {
  private RoomId roomId;
  private PlayerId hostPlayerId;
  private int maxPlayers;
  private int roundDuration;
  private int numberOfRounds;
  private String password;
  private List<String> categories;
  private List<String> excludedLetters;

  public RoomDto(RoomId roomId, PlayerId hostPlayerId, int maxPlayers, int roundDuration, int numberOfRounds, String password, List<String> categories, List<String> excludedLetters) {
    this.roomId = roomId;
    this.hostPlayerId = hostPlayerId;
    this.maxPlayers = maxPlayers;
    this.roundDuration = roundDuration;
    this.numberOfRounds = numberOfRounds;
    this.password = password;
    this.categories = categories;
    this.excludedLetters = excludedLetters;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RoomId getRoomId() {
    return roomId;
  }

  public void setRoomId(RoomId roomId) {
    this.roomId = roomId;
  }

  public PlayerId getHostPlayerId() {
    return hostPlayerId;
  }

  public void setHostPlayerId(PlayerId hostPlayerId) {
    this.hostPlayerId = hostPlayerId;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public int getRoundDuration() {
    return roundDuration;
  }

  public void setRoundDuration(int roundDuration) {
    this.roundDuration = roundDuration;
  }

  public int getNumberOfRounds() {
    return numberOfRounds;
  }

  public void setNumberOfRounds(int numberOfRounds) {
    this.numberOfRounds = numberOfRounds;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public List<String> getExcludedLetters() {
    return excludedLetters;
  }

  public void setExcludedLetters(List<String> excludedLetters) {
    this.excludedLetters = excludedLetters;
  }
}
