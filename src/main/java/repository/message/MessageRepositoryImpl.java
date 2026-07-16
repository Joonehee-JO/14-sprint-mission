package repository.message;

import domain.Message;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import repository.CrudRepository;

//그냥 추상클래스로 만드는게 나았을 것 같음 - 유저 리포지토리 메서드와 동일
public class MessageRepositoryImpl implements MessageRepository{
    private Long messageCount = 0L;
    private Map<Long, Message> messageList = new ConcurrentHashMap<>();

    @Override
    public Optional<Message> create(Message entity) {
        Message termMessage = new Message(++messageCount, entity.getUserId(), entity.getContent(), entity.getChannelId());
        messageList.put(termMessage.getMessageId(), termMessage);
        return Optional.of(termMessage);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(messageList.get(id));
    }

    @Override
    public Optional<Message> update(Message message) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Message>> findAllEntity() {
        return Optional.of(List.copyOf(messageList.values()));
    }

    @Override
    public Optional<List<Message>> findChannelAllMessage(Long channerId) {
        //리스트 스트림으로 뽑아서 필터링으로 해당 채널 메시지만 필터링해서 넘겨주기
        return Optional.of(List.copyOf(messageList.values().stream()
            .filter(message -> message.getChannelId().equals(channerId))
            .toList()));
    }
}
