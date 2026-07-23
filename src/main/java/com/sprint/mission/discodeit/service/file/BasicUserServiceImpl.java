package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.user.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicUserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(String name) {
        //유저제작
        User user = User.makeUser(name);
        if(userRepository.saveEntity(user) != null){
            return user;
        }
        throw new RuntimeException("유저 저장 실패");
    }

    @Override
    public User findById(UUID id) {
        User user = userRepository.findById(id);
        if( user!= null) {
            return user;
        };

        throw new RuntimeException("유저 찾기 실패");
    }

    @Override
    public User updateUser(UUID id, String name) {
        User user = userRepository.updateEntity(id, name);
        if(user == null)throw new RuntimeException("유저 업뎃 실패");
        return user;
    }

    @Override
    public List<User> findAllUser(){
        List<User> userList = findAllUser();
        return userList;
    }

    @Override
    public void deleteUser(UUID id) {
        if(userRepository.findById(id) != null){
            userRepository.deleteEntity(id);
        }
    }
}
