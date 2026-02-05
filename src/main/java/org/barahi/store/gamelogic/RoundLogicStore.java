package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.barahi.serviceapi.player.Player.PlayerId;
import org.jooq.DSLContext;

import java.util.Map;

public class RoundLogicStore {
  private final DSLContext db;

  @Inject
  public RoundLogicStore(DSLContextProvider dbProvider) {
    this.db = dbProvider.get();
  }

}
