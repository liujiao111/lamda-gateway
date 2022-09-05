package com.lamda.core.netty;

import com.lamda.core.LamdaConfig;
import com.lamda.core.LifeCycle;
import com.lamda.core.helper.AsyncHttpHelper;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import java.io.IOException;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 10:08
 * @modify.date 2022-09-03 10:08
 * @since 0.1
 */
@Slf4j
public class NettyHttpClient implements LifeCycle {


    private AsyncHttpClient asyncHttpClient;

    private LamdaConfig config;

    private DefaultAsyncHttpClientConfig.Builder clientBuilder;

    private EventLoopGroup workerEventLoopGroup;

    public NettyHttpClient(LamdaConfig config, EventLoopGroup workerEventLoopGroup) {
        this.config = config;
        this.workerEventLoopGroup = workerEventLoopGroup;

        init();
    }

    /**
     * 初始化AsyncHttpClient
     */
    @Override
    public void init() {
        this.clientBuilder = new DefaultAsyncHttpClientConfig.Builder()
                .setFollowRedirect(false)
                .setEventLoopGroup(workerEventLoopGroup)
                .setConnectTimeout(config.getHttpConnectTimeout())
                .setRequestTimeout(config.getHttpRequestTimeout())
                .setMaxRequestRetry(config.getHttpMaxRequestRetry())
                .setAllocator(PooledByteBufAllocator.DEFAULT)
                .setCompressionEnforced(true)
                .setMaxConnections(config.getHttpMaxConnections())
                .setMaxConnectionsPerHost(config.getHttpConnectionsPerHost())
                .setPooledConnectionIdleTimeout(config.getHttpPooledConnectionIdleTimeout());
    }

    @Override
    public void start() {
        this.asyncHttpClient = new DefaultAsyncHttpClient(clientBuilder.build());
        AsyncHttpHelper.getInstance().initialized(asyncHttpClient);
    }

    @Override
    public void shutdown() {
        if(asyncHttpClient != null) {
            try {
                asyncHttpClient.close();
            } catch (IOException e) {
                log.error("#NettyHttpClient.shutdown# shutdown error", e);
            }
        }
    }
}
