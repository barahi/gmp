package org.barahi.store.gamelogic;

import jakarta.inject.Inject;
import org.barahi.infra.DSLContextProvider;
import org.jooq.DSLContext;

public class GameLogicStore {
  private final DSLContext db;

  @Inject
  public GameLogicStore(DSLContextProvider dbProvider){
    this.db = dbProvider.get();
  }

}
