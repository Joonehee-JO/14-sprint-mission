package com.sprint.mission.discodeit.user.domain.repository;

import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.abstractmaprepository.CrudRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existAllById(List<UUID> idList);
}
