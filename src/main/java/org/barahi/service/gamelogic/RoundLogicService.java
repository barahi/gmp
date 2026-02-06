package org.barahi.service.gamelogic;

import java.util.List;
import java.util.Map;

import static org.barahi.serviceapi.player.Player.*;

public interface RoundLogicService {
    void storeAnswers(Map<String, String> playerAnswers, String category);

}
