package org.barahi.service.gamelogic;

import org.barahi.infra.Functional;
import org.barahi.serviceapi.player.Player.PlayerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RoundLogicServiceImpl implements RoundLogicService {
    private static final int CATEGORY_FULL_SCORE = 100;
    int roundNumber;

    @Override
    public void initializeRound(int numberOfRounds, List<String> categories, List<PlayerId> players) {
        // initialize data structures to hold player answers for each category
        roundNumber = 0;
    }

    @Override
    public Map<PlayerId, Integer> calculatePlayerScoreForRound(Map<String, Map<PlayerId, String>> categoryToPlayerAnswers, List<PlayerId> playerIds) {
        Map<PlayerId, Integer> roundScores = Functional.createMap(playerIds, Function.identity(), id -> 0);
        Map<String, Integer> answerCount = new HashMap<>();

        categoryToPlayerAnswers.forEach((category, playerIdToAnswers) -> {
            playerIdToAnswers.forEach((playerId, answer) -> {
                if (answer != null && !answer.isEmpty()) {
                    answerCount.put(answer, answerCount.getOrDefault(answer, 0) + 1);
                }
            });
        });

        categoryToPlayerAnswers.forEach((category, playerIdToAnswers) -> {
            playerIdToAnswers.forEach((playerId, answer) -> {
                if (answerCount.containsKey(answer)){
                    int score = CATEGORY_FULL_SCORE / (answerCount.get(answer));
                    roundScores.put(playerId, roundScores.get(playerId) + score);
                }
            });
        });
        return roundScores;

        // void flagScore()
    }
}

