package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository <T, ID>{
    Optional<T> saveEntity(T entity);
    Optional<T> findById(ID id);
    Optional<T> updateEntity(ID id);
    void deleteEntity(ID id);
    Optional<List<T>> findAllEntity();
}
