package repository;

import domain.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryImpl implements CrudRepository<User, Long>{
    private Long userConut = 0L;
    private Map<Long, User> userList = new ConcurrentHashMap<>();


    //DTO로 받아야하는지 서비스 계층 (1개) + 여기서 1개 2개의 유저가 생기는데 이게 맞는지
    @Override
    public Optional<User> create(User entity) {
        if(userList.containsKey(entity.getUserId())){
            throw new RuntimeException("이미 해당 회원 존재");
        }
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
