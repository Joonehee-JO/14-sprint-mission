package controller.message;

import domain.Message;
import global.annotation.Comment;
import java.util.List;
import service.message.MessageService;

public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Comment("채널 컨트롤러에서 최초 채널 입장 / 메시지 입력마다 호출되는 컨트롤러")
    public List<Message> channelAdmitExtractMessage(Long channelId){
        //뷰 렌더링
        return messageService.extractAllChannelMessage(channelId);
    }

    @Comment("채널 입장해서 메시지 입력 시 호출되는 컨트롤러")
    public void channelInputMessage(MessageDTO messageDTO){
        messageService.inputMessage(messageDTO.getChannelId(), messageDTO.getUserId(), messageDTO.getContent());
    }
}
