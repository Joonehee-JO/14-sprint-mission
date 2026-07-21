package repository.UserRepository;

import domain.User;
import global.annotation.Comment;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import repository.CrudRepository;

public class UserRepositoryImpl implements CrudRepository<User, Long> {
    private Long userConut = 0L;
    private Map<Long, User> userList = new ConcurrentHashMap<>();


    //DTO로 받아야하는지 서비스 계층 (1개) + 여기서 1개 2개의 유저가 생기는데 이게 맞는지
    @Override
    public Optional<User> create(User entity) {
        //초기에 여기서 에외던졌는데 빈상자를 보내고 서비스계층에서 예외던지는게 맞다고 생각
        //보니까 엔터티 아이디 널값인데 그거 꺼내서 비교하려해서 런타임에러가 남
        /*if(userList.containsKey(entity.getUserId())){
            throw new RuntimeException("이미 해당 회원 존재");
        }*/
        User termUser = new User(++userConut, entity.getName());
        userList.put(termUser.getUserId(), termUser);
        return Optional.of(termUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userList.get(id));
    }

    @Override
    public Optional<User> update(User user) {
        //추후구현
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> findAllEntity() {
        //수정못하게
        return Optional.of(List.copyOf(userList.values()));
    }
}
