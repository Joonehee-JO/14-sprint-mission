package repository;

import java.util.List;
import java.util.Optional;

/*
    //궁금한것
    원래 인터페이스 -> 임플 클래스로 1대1 매핑하는것으로 구현했었는데
    수업시간에 추상클래스로 뼈대를 만들어놓고 그걸 유저 메시지 채널 각 리포지토리 클래스로 상속받아서 쓸지 고민했음
    근데 뭔가 각 리포지토리에 추가 기능이 생기면 이걸 어떻게 수정해야할지 잘 안와닿아서(인터페이스를 다시 따로 만들어야할지?)
    추상클래스 없이 구현하는 것으로 결정
 */

/*
    기본 CRUD 가능 리포지토리 > 메모리 - JDBC 갈아끼기 위함
 */
public interface CrudRepository <T,ID>{
    Optional<T> create(T entity);
    Optional<T> findById(ID id);
    Optional<T> update(T t);
    Optional<List<T>> findAllEntity();
}
