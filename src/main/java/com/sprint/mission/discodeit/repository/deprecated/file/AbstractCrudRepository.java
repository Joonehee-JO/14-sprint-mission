//package com.sprint.mission.discodeit.repository.file;
//
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.entity.IdMapper;
//import com.sprint.mission.discodeit.repository.CrudRepository;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//
//
//@RequiredArgsConstructor
//abstract public class AbstractCrudRepository<T extends IdMapper> implements FileCrudRepository<T, UUID> {
//    //static final String filePath = "src/main/java/";
//    //static final String fileName = "channel.ser";
//
//    /*
//        리스트 전부 불러와서 리스트 추가해서 덮어쓰기
//
//        중복 엔터티가 들어와도 이를 검증할 방법이 없어서 다른 자룍구조를 추천해주셨는데 이미 모든 코드가 리스트형태로 짜여져있어서
//        일단 리스트로 안에 로직에서 반복하여 검증하는 것으로 수정
//
//        근데 이렇게 반복문으로 돌려보려다보니까 이건 제네릭으로 명시해서 자바는 오브젝트 타입으로 엔터티를 인식하기에 게터메서드를 사용불가
//        그럼 나는 게터 메서드를 여기에 추상메서드로 남기고 구현체에서 오버라이딩해서 UUID 를 직접 가져오기로 결정
//
//        AI를 통해 알게된 것 -> T extends CustomClass 로 정의해서 컴파일러가 T를 해당 클래스를 상속받은 객체로써 인식하기로 변경하고
//        단순하게 이 리포지토리를 이용할 엔터티들은 UUID를 반환하는 메서드를 갖는 인터페이스를 구현한 친구들만 이용할 수 있게 변경
//        (대신 이 추상클래스로 들어오는 엔터티들은 모두 IdMapper인터페이스를 구현해야만함)
//     */
//    @Override
//    public T saveEntity(T entity) {
//        List<T> savedList = findAllEntity();
//
//        boolean isDuplicate = savedList.stream()
//            .anyMatch(savedEntity -> savedEntity.getId().equals(entity.getId()));
//
//        if(isDuplicate) return entity;
//
//        try(FileOutputStream fileOutputStream = new FileOutputStream(getFilePath());
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
//            savedList.add(entity);
//            objectOutputStream.writeObject(savedList);
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return entity;
//    }
//
//    //T 가 뭔지 정의 못해서 getID 불가하여 추상메서드로 만들고 구현체에서 완성하도록...
//    @Override
//    abstract public T findById(UUID uuid);
//
//    @Override
//    abstract public T updateEntity(UUID uuid, String updateName);
//
//    @Override
//    abstract public void deleteEntity(UUID uuid);
//
//    @Override
//    public List<T> findAllEntity() {
//        try(FileInputStream fileInputStream = new FileInputStream(getFilePath());
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
//            return (List<T>) objectInputStream.readObject();
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return new ArrayList<>();
//    }
//
//    abstract protected String getFilePath();
//}
