package sprint0.service.user;

import sprint0.domain.User;
import global.exception.CustomErrorCode;
import global.exception.CustomException;
import java.util.Optional;
import sprint0.repository.CrudRepository;

public class UserServiceImpl implements UserService{
    private final CrudRepository userRepository;

    /*
        롬복 못써서 생성자 열구 컨피그에서 주입하려합니다
     */
    public UserServiceImpl(CrudRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long initUser(String name) {
        Optional<User>user =userRepository.create(User.makeUser(name));
        if(user.isEmpty()) throw new CustomException(CustomErrorCode.INVALID_USER_INIT);

        return user.get().getUserId();
    }
}
