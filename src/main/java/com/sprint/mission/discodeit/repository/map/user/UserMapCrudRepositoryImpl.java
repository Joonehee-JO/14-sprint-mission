package com.sprint.mission.discodeit.repository.map.user;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.map.AbstractMapCrudRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserMapCrudRepositoryImpl extends AbstractMapCrudRepository<User> implements UserRepository {

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> allEntity = super.findAllEntity();
        return allEntity.stream()
            .filter(entity -> entity.getEmail().equals(email))
            .findFirst();       //얘가 알아서 옵셔널로 감싸서 리턴해준다함
    }
}
