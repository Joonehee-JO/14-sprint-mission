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
    /*
        추가할게 있다면 추상클래스가 바라본 CrudRepository를 상속받은 새로운 인터페이스를 추가 후
        정의하고자 하는 메서드 프로토타입을 선언하여 여기서 구현하면됨.
        서비스 계층에서는 해당 인터페이스를 참조변수로 바라보게 하기
     */

    //추상클래스에서 맵을 들고있어서 super로 모든리스트 가져와야함...
    @Override
    public Optional<User> findByEmail(String email) {
        List<User> allEntity = super.findAllEntity();
        return allEntity.stream()
            .filter(entity -> entity.getEmail().equals(email))
            .findFirst();       //얘가 알아서 옵셔널로 감싸서 리턴해준다함
    }
}
