package org.barahi.store;

import org.barahi.store.gamelogic.CumulativeScoreStore;
import org.barahi.store.gamelogic.GameStateStore;
import org.barahi.store.gamelogic.PlayerAnswerStore;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class StoreBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PlayerStore.class).to(PlayerStore.class);
        bind(RoomStore.class).to(RoomStore.class);
        bind(GameSettingsStore.class).to(GameSettingsStore.class);
        bind(CumulativeScoreStore.class).to(CumulativeScoreStore.class);
        bind(PlayerAnswerStore.class).to(PlayerAnswerStore.class);
        bind(GameStateStore.class).to(GameStateStore.class);
    }
}
