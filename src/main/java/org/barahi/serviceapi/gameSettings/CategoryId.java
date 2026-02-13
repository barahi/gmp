package org.barahi.serviceapi.gameSettings;

import org.barahi.infra.TypedUUID;

import java.util.UUID;

public class CategoryId extends TypedUUID<CategoryId> {
  public CategoryId (UUID uuid) {
    super(uuid);
  }

  public static CategoryId of(UUID id) {
    return new CategoryId(id);
  }

  public static CategoryId of(String id) throws IllegalArgumentException {
    return CategoryId.of(UUID.fromString(id));
  }

  public static CategoryId newId() {
    return new CategoryId(UUID.randomUUID());
  }
}
