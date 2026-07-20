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

    @Deprecated(since = "2026-07", forRemoval = false)  // 뷰 렌더링 부분 통합 후 구현하고자 미완성인거 알리려고 함
    @Comment("채널 컨트롤러에서 최초 채널 입장 / 메시지 입력마다 호출되는 컨트롤러")
    public void channelAdmitExtractMessage(Long channelId){
        List<Message> messageList = messageService.extractAllChannelMessage(channelId);

        //뷰 렌더링
    }

    @Comment("채널 입장해서 메시지 입력 시 호출되는 컨트롤러")
    public void channelInputMessage(MessageDTO messageDTO){
        messageService.inputMessage(messageDTO.getChannelId(), messageDTO.getUserId(), messageDTO.getContent());
    }
}
