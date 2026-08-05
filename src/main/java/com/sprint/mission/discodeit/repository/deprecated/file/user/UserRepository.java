package com.sprint.mission.discodeit.repository.deprecated.file.user;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.deprecated.file.FileCrudRepository;
import java.util.UUID;

public interface UserRepository extends FileCrudRepository<User, UUID> {

}
