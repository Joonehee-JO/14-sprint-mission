package com.sprint.mission.discodeit.user.domain.repository.map;

import com.sprint.mission.discodeit.user.domain.entity.UserStatus;
import com.sprint.mission.discodeit.abstractmaprepository.map.AbstractMapCrudRepository;
import com.sprint.mission.discodeit.user.domain.repository.UserStatusRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class MapUserStatusCrudRepositoryImpl extends AbstractMapCrudRepository<UserStatus> implements
    UserStatusRepository {

    @Override
    public Optional<UserStatus> findUserStatusByUserId(UUID userId) {
        List<UserStatus> userStatusList = super.findAllEntity();
        return userStatusList.stream()
            .filter(userStatus -> userStatus.getUserId().equals(userId))
            .findFirst();
    }
}
