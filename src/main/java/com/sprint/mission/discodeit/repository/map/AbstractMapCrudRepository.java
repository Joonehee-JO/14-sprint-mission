package com.sprint.mission.discodeit.repository.map;

import com.sprint.mission.discodeit.entity.IdMapper;
import com.sprint.mission.discodeit.repository.CrudRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/*
    --------todo : 복습-------
    옵셔널을 쓰는 이유 - 우리의 스프링 프로그램이 돌아가는 서버의 상황과 실제 데이터베이스 서버의 상황이 다르기 때문에
    디비로부터 개체를 받아오는 경우에 널값이 발생할 수 있음. 이러면 서비스 계층에서는 이 널을 처리하지 않을 경우 런타임 에러가 발생할 수 있기에
    컴파일타임에 확실히 하기위한 옵셔널을 쓰는 것이고 옵셔널의 다양한 메서드로 가독성 좋게 코드를 짨 수 있어짐.
 */

/*
    이해가 안됐던게 단건조회는 옵셔널을 쓰고 다건조회는 옵셔널을 왜 안쓰는지 거였는데
    단건 조회는 개체 자체에 접근해서 get으로 꺼내는 순간이 위험하기에 이게 비어있는지 열어보고 까면 되지만
    다건조회 또한 idx 접근으로 개체 자체에 접근해서 런타임 에러가 날 수 있지만 실제 코드를 보면

    Optional<List<Comment>> optionalList = commentRepository.findAll();
    if (optionalList.isPresent()) {
        List<Comment> comments = optionalList.get();
        if (!comments.isEmpty()) {
            Comment comment = comments.get(0);
        }
    }

    이런식으로 상자가 들어있는지 체크하고 그걸 까고 밸류를 꺼내오는 작업을 손수해야하기에

    List<Comment> comments = commentRepository.findAll();
    Optional<Comment> firstComment = comments.stream().findFirst();

    if (!comments.isEmpty()) {
        Comment first = comments.get(0);
    }

    리스트 밸류 자체에 접근하는 것은 이렇게 해야함
*/

public abstract class AbstractMapCrudRepository<T extends IdMapper> implements CrudRepository<T, UUID> {
    private final Map<UUID, T> entityList = new ConcurrentHashMap<>();

    @Override
    public T saveEntity(T entity) {
        entityList.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(entityList.get(id));
    }

    @Override
    public void deleteEntity(UUID id) {
        entityList.remove(id);
    }

    /*
    ------- todo : 복습용 -----------
        이거도 옵셔널을 감싸 던졌었는데 모든 개체를 추출하는 메서드에서 옵셔널로 감싸 던지는건 안티패턴이라고함.
     */
    @Override
    public List<T> findAllEntity() {
        return new ArrayList<>(entityList.values());
    }
}
