package org.barahi.service.gamelogic;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.barahi.service.gamelogic.RoundLogicHelper.generateRandomCharExcluding;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;

public class RoundLogicHelperTest {
  @Nested
  public class GenerateRandomCharExcluding {
    @RepeatedTest(1000)
    public void test() {
      List<Character> exclusion = List.of('x', 'y', 'z', 'v', 'p', 'q', 'w');
      assertThat(generateRandomCharExcluding(exclusion), not(in(exclusion)));
    }
  }
}
