package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, UUID>{
    Optional<Message> findLastMessageByChannelId(UUID channelId);
    // 모든 메시지를 가져와서 필터링으로 필요 데이터를 리턴하게한다
    // db state문으로 where 조건을 걸어서 필요 데이터만 가져온다
    /*
        todo-----------------복습용
        jpql base 3가지 경우
            +
        mybatis 프로그래머 전부 작성된거없이 수기로 전부 다해야함
     */

    void deleteMessageByChannelId(UUID channelId);
    List<Message> findAllMessageByChannelId(UUID channelId);
}
