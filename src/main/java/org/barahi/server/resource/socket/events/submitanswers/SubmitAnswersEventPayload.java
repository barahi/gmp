package org.barahi.server.resource.socket.events.submitanswers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.barahi.server.resource.socket.EventPayload;
import org.barahi.server.serializer.PlayerSerializer;
import org.barahi.serviceapi.gameSettings.CategoryId;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.Map;

public class SubmitAnswersEventPayload implements EventPayload {
  private CategoryId categoryId;
  private int roundNumber;
  @JsonDeserialize(keyUsing = PlayerSerializer.PlayerIdKeyDeserializer.class)
  private Map<PlayerId, String> playerAnswers;

  public SubmitAnswersEventPayload(CategoryId categoryId, int roundNumber, Map<PlayerId, String> playerAnswers) {
    this.categoryId = categoryId;
    this.roundNumber = roundNumber;
    this.playerAnswers = playerAnswers;
  }

  public CategoryId getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(CategoryId categoryId) {
    this.categoryId = categoryId;
  }

  public int getRoundNumber() {
    return roundNumber;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public Map<PlayerId, String> getPlayerAnswers() {
    return playerAnswers;
  }

  public void setPlayerAnswers(Map<PlayerId, String> playerAnswers) {
    this.playerAnswers = playerAnswers;
  }
}
