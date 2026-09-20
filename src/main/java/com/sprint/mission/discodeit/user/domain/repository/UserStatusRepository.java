package com.sprint.mission.discodeit.user.domain.repository;

import com.sprint.mission.discodeit.user.domain.entity.UserStatus;
import com.sprint.mission.discodeit.abstractmaprepository.CrudRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository extends CrudRepository<UserStatus, UUID> {
    Optional<UserStatus> findUserStatusByUserId(UUID userId);
}
