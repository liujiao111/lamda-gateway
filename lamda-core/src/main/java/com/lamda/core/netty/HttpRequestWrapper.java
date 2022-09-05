package com.lamda.core.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

/**
 * lamda File Description
 *  request请求对象封装类
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 15:32
 * @modify.date 2022-09-03 15:32
 * @since 0.1
 */
@Data
public class HttpRequestWrapper {

    private FullHttpRequest fullHttpRequest;

    private ChannelHandlerContext ctx;

    public HttpRequestWrapper(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        this.fullHttpRequest = fullHttpRequest;
        this.ctx = ctx;
    }
}
