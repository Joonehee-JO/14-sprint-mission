package repository.message;

import domain.Message;
import global.annotation.Comment;
import java.util.List;
import java.util.Optional;
import repository.CrudRepository;

/*
    기존 CRUD 리포지토리만으로는 한계를 느껴서 메시지 리포지토리 인터페이스로 확장
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
    @Comment("채널 별 모든 메시지 조회")
    Optional<List<Message>> findChannelAllMessage(Long channerId);

    //안쓸듯
//    @Comment("해당 채널 특정 유저 메시지 조회")
//    Optional<List<Message>> findChannelUserAllMessage(Long channerId, Long userId);
}
