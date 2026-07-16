package repository;

import static org.junit.jupiter.api.Assertions.*;

import domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository.UserRepositoryImpl;

class UserRepositoryImplTest {
    private CrudRepository crudRepository;

    @BeforeEach
    public void init(){
        crudRepository = new UserRepositoryImpl();
        User aUser = new User(1L, "jh");
        User bUser = new User(2L, "jh2");
        crudRepository.create(aUser);
        crudRepository.create(bUser);
    }

    @Test
    public void 유저_입력_성공(){
        User inputUser = new User(3L, "tset");
        Optional<User> checkUser = crudRepository.create(inputUser);
        //assertThat(inputUser).isEqualTo(checkUser);
        assertThat(checkUser.get().getUserId()).isEqualTo(3L);
        assertThat(checkUser.get().getName()).isEqualTo(inputUser.getName());
    }

    @Test
    public void 유저_입력_실패(){
        User inputUser = new User(2L, "test");
        //Optional<User> checkUser = crudRepository.create(inputUser);
        //assertThat(checkUser).isEmpty();
        assertThrows(RuntimeException.class, () -> {
            crudRepository.create(inputUser);
        });
    }

    @Test
    public void 유저_전부_추출_카운트비교(){
        Optional<List<User>> box = crudRepository.findAllEntity();
        List<User>list = box.orElse(new ArrayList<>());

        assertThat(list.size()).isEqualTo(2 );
    }

    @Test
    public void 유저_찾기(){
        Optional<User> checkUser = crudRepository.findById(1L);
        assertThat(checkUser.get().getName()).isEqualTo("jh");
    }
}