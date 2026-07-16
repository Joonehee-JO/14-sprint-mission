package service.user;

import domain.User;
import java.util.Optional;
import repository.CrudRepository;

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
        if(user.isEmpty()) throw new RuntimeException("에러 발생유");

        return user.get().getUserId();
    }
}
