package com.lamda.core.netty.peocessor;

import com.lamda.core.netty.HttpRequestWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 16:01
 * @modify.date 2022-09-03 16:01
 * @since 0.1
 */
public class NettyCoreProcessor implements NettyProcessor{

    @Override
    public void process(HttpRequestWrapper requestWrapper) {
        final FullHttpRequest fullHttpRequest = requestWrapper.getFullHttpRequest();
        final ChannelHandlerContext ctx = requestWrapper.getCtx();
        try {
            System.out.println("接收到HTTP请求");
            //	1. 解析FullHttpRequest, 把他转换为我们自己想要的内部对象：Context

            //	2. 执行整个的过滤器逻辑：FilterChain

        } catch (Throwable t) {
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }
}
