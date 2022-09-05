package com.lamda.core.netty;

import com.lamda.core.netty.peocessor.NettyProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 10:33
 * @modify.date 2022-09-03 10:33
 * @since 0.1
 */
@Slf4j
public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {

    private NettyProcessor nettyProcessor;

    public NettyHttpServerHandler(NettyProcessor nettyProcessor) {
        this.nettyProcessor = nettyProcessor;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest)msg;
            HttpRequestWrapper httprequestWrapper = new HttpRequestWrapper(fullHttpRequest, ctx);
            nettyProcessor.process(httprequestWrapper);
        } else {
            log.error("#NettyHttpServerHandler.channelRead# message type is not httpRequest: {}", msg);
            boolean release = ReferenceCountUtil.release(msg);
            if(!release) {
                log.error("#NettyHttpServerHandler.channelRead# release fail 资源释放失败");
            }
        }
    }
}
