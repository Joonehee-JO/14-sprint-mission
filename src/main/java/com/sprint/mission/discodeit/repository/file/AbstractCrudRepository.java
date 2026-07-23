package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.CrudRepository;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract public class AbstractCrudRepository<T> implements FileCrudRepository<T, UUID> {
    //static final String filePath = "src/main/java/";
    //static final String fileName = "channel.ser";

    /*
        리스트 전부 불러와서 리스트 추가해서 덮어쓰기
     */
    @Override
    public T saveEntity(T entity) {
        List<T> savedList = findAllEntity();

        try(FileOutputStream fileOutputStream = new FileOutputStream(getFilePath());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            savedList.add(entity);
            objectOutputStream.writeObject(savedList);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return entity;
    }

    //T 가 뭔지 정의 못해서 getID 불가하여 추상메서드로 만들고 구현체에서 완성하도록...
    @Override
    abstract public T findById(UUID uuid);

    @Override
    abstract public T updateEntity(UUID uuid, String updateName);

    @Override
    abstract public void deleteEntity(UUID uuid);

    @Override
    public List<T> findAllEntity() {
        try(FileInputStream fileInputStream = new FileInputStream(getFilePath());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            return (List<T>) objectInputStream.readObject();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    abstract protected String getFilePath();
}
