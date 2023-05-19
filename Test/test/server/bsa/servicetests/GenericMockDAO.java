package server.bsa.servicetests;

import bsa.dataaccess.exceptions.DataAccessException;
import bsa.models.Entity;
import bsa.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class GenericMockDAO<T extends Entity> {

    public List<T> savedEntities;

    public GenericMockDAO() {
        savedEntities = new ArrayList<>();
    }

    public void create(T entity) {
        savedEntities.add(entity);
    }

    public T find(UUID id) {
        for (T entity : savedEntities) {
            if(entity.getId().equals(id)) return entity;
        }
        throw new DataAccessException("Entity with id " + id + " not found");
    }

    public void update(T entity) {
        boolean wasFound = savedEntities.removeIf(e -> e.getId().equals(entity.getId()));
        if(!wasFound){
            throw new DataAccessException("Entity with id " + entity.getId() + " not found");
        }
        savedEntities.add(entity);
    }

    public void delete(UUID id){
        boolean wasFound = savedEntities.removeIf(e -> e.getId().equals(id));
        if(!wasFound){
            throw new DataAccessException("User with id " + id + " not found");
        }
    }
}
