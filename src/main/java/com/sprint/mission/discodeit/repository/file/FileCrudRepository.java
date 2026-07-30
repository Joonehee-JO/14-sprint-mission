package com.sprint.mission.discodeit.repository.file;

import java.util.List;

/*
    파일형식 저장용 인터페이스
 */
public interface FileCrudRepository <T, ID>{
    T saveEntity(T entity);
    T findById(ID id);
    T updateEntity(ID id, String updateName);
    void deleteEntity(ID id);
    List<T> findAllEntity();
}
