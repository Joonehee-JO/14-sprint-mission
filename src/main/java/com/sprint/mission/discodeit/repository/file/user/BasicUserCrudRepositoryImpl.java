//package com.sprint.mission.discodeit.repository.file.user;
//
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.repository.file.AbstractCrudRepository;
//import java.util.List;
//import java.util.UUID;
//import org.springframework.stereotype.Repository;
//
////@Repository
//public class BasicUserCrudRepositoryImpl extends AbstractCrudRepository<User> implements UserRepository{
//    private static final String filePath = "src/main/java/";
//    private static final String fileName = "user.ser";
//
//    @Override
//    public User findById(UUID uuid) {
//        List<User> userList = findAllEntity();
//        for (User user : userList) {
//            if(user.getId().equals(uuid)){
//                return user;
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public User updateEntity(UUID uuid, String updateName) {
//        User updateUser = findById(uuid);
//
//        //서비스에서 파인드바이아이디 호출하고 존재하면 이 메서드를 호출하게 수정
////        if(updateUser != null) {
////            updateUser.update(updateName);
////            return updateUser;
////        }
//        updateUser.update(updateName);
//        return updateUser;
//    }
//
//    @Override
//    public void deleteEntity(UUID uuid) {
//        List<User> userList = findAllEntity();
//        /*
//            해당 포문으로 리스트를 돌리는 순간 내부적으로 count를 세서 반복을 수행하는데
//            리무브를 하는 순간 count수와 리스트 내부의 개체수가 달라
//            CME ConcurrentModificationException가 터지게됨.
//            따라서 iterator 를 붙여? while문으로 리스트가 남아있는지 계속 체크하여 돌거나
//            리스트 셋의 메서드인 리무브이프를 써야함
//         */
////        for (User user : userList) {
////            if(user.getId().equals(uuid)){
////                userList.remove(user);
////            }
////        }
//
//        userList.removeIf(user -> user.getId().equals(uuid));
//    }
//
//    @Override
//    protected String getFilePath() {
//        return filePath + fileName;
//    }
//}
