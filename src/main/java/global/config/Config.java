package global.config;

import sprint0.controller.channel.ChannelController;
import sprint0.controller.message.MessageController;
import sprint0.controller.user.UserController;
import sprint0.repository.CrudRepository;
import sprint0.repository.UserRepository.UserRepositoryImpl;
import sprint0.repository.message.MessageRepository;
import sprint0.repository.message.MessageRepositoryImpl;
import sprint0.service.channel.ChannelService;
import sprint0.service.channel.ChannelServiceImpl;
import sprint0.service.message.MessageService;
import sprint0.service.message.MessageServiceImpl;
import sprint0.service.user.UserService;
import sprint0.service.user.UserServiceImpl;

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
