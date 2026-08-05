package com.sprint.mission.discodeit.repository.map;

import com.sprint.mission.discodeit.entity.IdMapper;
import com.sprint.mission.discodeit.repository.CrudRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/*
    --------todo : 복습-------
    옵셔널을 쓰는 이유 - id로 조회하는 단건 조회의 경우 디비에 튜플이 존재하지 않는 상황을 대비하기 위해서
    이를 방지하지 않으면 어디서 널포인터익셉션이 터질지 모르는 상태가 됨
 */

public abstract class AbstractMapCrudRepository<T extends IdMapper> implements CrudRepository<T, UUID> {
    private final Map<UUID, T> entityList = new ConcurrentHashMap<>();

    @Override
    public T saveEntity(T entity) {
        entityList.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(entityList.get(id));
    }

    @Override
    public void deleteEntity(UUID id) {
        entityList.remove(id);
    }

    /*
    ------- todo : 복습용 -----------
        이거도 옵셔널을 감싸 던졌었는데 모든 개체를 추출하는 메서드에서 옵셔널로 감싸 던지는건 안티패턴이라고함.
     */
    @Override
    public List<T> findAllEntity() {
        return new ArrayList<>(entityList.values());
    }
}
