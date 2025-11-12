package org.barahi.infra;

import java.util.List;
import java.util.UUID;

public abstract class TypedUUID<T> extends Id<T, UUID> {
    protected TypedUUID(UUID id) {
        super(id);
    }

    public List<UUID> getIds(List<TypedUUID<? extends T>> typedIds) {
        return typedIds.stream().map(TypedUUID::getId).toList();
    }
}
