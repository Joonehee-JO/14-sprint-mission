package com.sprint.mission.discodeit.repository.file.user;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.AbstractCrudRepository;
import java.util.List;
import java.util.UUID;

public class BasicUserCrudRepositoryImpl extends AbstractCrudRepository<User> implements UserRepository{
    private static final String filePath = "src/main/java/";
    private static final String fileName = "user.ser";

    @Override
    public User findById(UUID uuid) {
        List<User> userList = findAllEntity();
        for (User user : userList) {
            if(user.getId().equals(uuid)){
                return user;
            }
        }

        return null;
    }

    @Override
    public User updateEntity(UUID uuid, String updateName) {
        User updateUser = findById(uuid);

        //서비스에서 파인드바이아이디 호출하고 존재하면 이 메서드를 호출하게 수정
//        if(updateUser != null) {
//            updateUser.update(updateName);
//            return updateUser;
//        }
        updateUser.update(updateName);
        return updateUser;
    }

    @Override
    public void deleteEntity(UUID uuid) {
        List<User> userList = findAllEntity();
        for (User user : userList) {
            if(user.getId().equals(uuid)){
                userList.remove(user);
            }
        }
    }

    @Override
    protected String getFilePath() {
        return filePath + fileName;
    }
}
