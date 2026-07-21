package global.config;

import controller.channel.ChannelController;
import controller.message.MessageController;
import controller.user.UserController;
import repository.CrudRepository;
import repository.UserRepository.UserRepositoryImpl;
import repository.message.MessageRepository;
import repository.message.MessageRepositoryImpl;
import service.channel.ChannelService;
import service.channel.ChannelServiceImpl;
import service.message.MessageService;
import service.message.MessageServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;

public class Config {
    private final CrudRepository userRepository = new UserRepositoryImpl();
    private final MessageRepository messageRepository = new MessageRepositoryImpl();

    //서비스
    private final UserService userService = new UserServiceImpl(userRepository);
    private final ChannelService channelService = new ChannelServiceImpl();
    private final MessageService messageService = new MessageServiceImpl(messageRepository);

    //컨트롤러
    private final UserController userController = new UserController(userService);
    private final ChannelController channelController = new ChannelController(channelService);
    private final MessageController messageController = new MessageController(messageService);


    public CrudRepository getUserRepository() {
        return userRepository;
    }

    public MessageRepository getMessageRepository() {
        return messageRepository;
    }

    public ChannelService getChannelService() {
        return channelService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public UserService getUserService() {
        return userService;
    }

    public ChannelController getChannelController() {
        return channelController;
    }

    public MessageController getMessageController() {
        return messageController;
    }

    public UserController getUserController() {
        return userController;
    }
}
