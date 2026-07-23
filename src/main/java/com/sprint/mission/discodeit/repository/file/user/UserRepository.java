package com.sprint.mission.discodeit.repository.file.user;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileCrudRepository;
import java.util.UUID;

public interface UserRepository extends FileCrudRepository<User, UUID> {

}
