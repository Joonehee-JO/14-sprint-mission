package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Message;
import domain.User;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.message.MessageRepository;
import repository.message.MessageRepositoryImpl;

class MessageRepositoryImplTest {
    MessageRepository messageRepository;
    User user;

    @BeforeEach
    public void init(){
        messageRepository = new MessageRepositoryImpl();
        user = new User(1L, "jh");
        messageRepository.create(new Message(null, user.getUserId(), "test", 2L));
        messageRepository.create(new Message(null, user.getUserId(), "test2", 2L));

    }

    @Test
    public void 메시지_생성(){
        //메시지 생성
        Message message = new Message(null, user.getUserId(), "방가워연", 1L);
        Optional<Message> checkMessage = messageRepository.create(message);
        assertThat(checkMessage.get().getChannelId()).isEqualTo(message.getChannelId());
        assertThat(checkMessage.get().getMessageId()).isEqualTo(3L  );
    }

    @Test
    public void 메시지_추출(){
        //dsa
        Optional<Message> checkMessage = messageRepository.findById(1L);
        assertThat(checkMessage.get().getMessageId()).isEqualTo(1L);
        assertThat(checkMessage.get().getChannelId()).isEqualTo(2L);
        assertThat(checkMessage.get().getContent()).isEqualTo("test");
    }

    @Test
    public void 특정채널_메시지_추출(){
        Optional<List<Message>> messageList = messageRepository.findChannelAllMessage(2L);
        assertThat(messageList).isNotEmpty();
        assertThat(messageList.get().size()).isEqualTo(2);
        assertThat(messageList.get().get(0).getChannelId()).isEqualTo(2L);
    }
}