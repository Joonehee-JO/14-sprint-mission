package controller.channel;

import global.annotation.Comment;
import service.channel.ChannelService;

public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Comment("채널 입장 컨트롤러")
    public Long admitChannel(ChannelDTO channelDTO) throws Exception{
        return channelService.moveChannel(channelDTO.getChannelId(), channelDTO.getUserId());
    }
}
