package com.lamda.core.netty;

import com.lamda.common.utils.RemotingHelper;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 10:32
 * @modify.date 2022-09-03 10:32
 * @since 0.1
 */
@Slf4j
public class NettyServerConnectManagerHandler extends ChannelDuplexHandler {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String remoteAddr = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
        log.debug("#NettyServerConnectManagerHandler# channelRegistered：{}", remoteAddr);
        super.channelRegistered(ctx);

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        String remoteAddr = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
        log.debug("#NettyServerConnectManagerHandler# channelUnregistered：{}", remoteAddr);
        super.channelUnregistered(ctx);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddr = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
        log.debug("#NettyServerConnectManagerHandler# channelActive：{}", remoteAddr);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddr = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
        log.debug("#NettyServerConnectManagerHandler# channelInactive：{}", remoteAddr);
        super.channelInactive(ctx);

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String remoteAddr = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state().equals(IdleState.ALL_IDLE)) {
                //空闲状态
                log.warn("NETTY SERVER PIPELINE : userEventTriggered : IDLE : {}", remoteAddr);
                ctx.channel().close();
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String remoteAddr = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
        log.warn("#NettyServerConnectManagerHandler# exceptionCaught：{}, 信息：{}", remoteAddr, cause);
        super.channelInactive(ctx);
    }
}
