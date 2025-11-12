package org.barahi.infra;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Objects;

public abstract class Id<T, K> implements Serializable {
    private final K id;

    public K getId() {
        return id;
    }

    protected Id(K id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Id<?, ?> id1 = (Id<?, ?>) o;
        return Objects.equals(getId(), id1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
