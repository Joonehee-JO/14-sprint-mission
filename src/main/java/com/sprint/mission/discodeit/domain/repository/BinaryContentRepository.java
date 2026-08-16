package com.sprint.mission.discodeit.domain.repository;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class BinaryContentRepository {
    private final Map<UUID, BinaryContent> store = new HashMap<>();

    public BinaryContent save(BinaryContent item) {
        store.put(item.getId(), item);
        return item;
    }

    public Optional<BinaryContent> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public void delete(UUID id){
        store.remove(id);
    }
}
