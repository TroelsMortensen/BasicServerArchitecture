package bsa.dataaccess;

import bsa.models.Entity;

import java.util.UUID;

public interface DAO<T extends Entity> {
    void create(T task);
    T find(UUID id);
    void update(T task);
    void delete(UUID id);
}
