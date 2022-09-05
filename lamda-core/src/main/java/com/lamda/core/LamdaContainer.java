package com.lamda.core;

import com.lamda.core.helper.LamdaBufferHelper;
import com.lamda.core.netty.NettyHttpClient;
import com.lamda.core.netty.NettyHttpServer;
import com.lamda.core.netty.peocessor.NettyBatchEventProcessor;
import com.lamda.core.netty.peocessor.NettyCoreProcessor;
import com.lamda.core.netty.peocessor.NettyMpmcProcessor;
import com.lamda.core.netty.peocessor.NettyProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 10:07
 * @modify.date 2022-09-03 10:07
 * @since 0.1
 */
@Slf4j
public class LamdaContainer implements LifeCycle{

    private LamdaConfig lamdaConfig;

    private NettyHttpServer nettyHttpServer;

    private NettyHttpClient nettyHttpClient;

    private NettyProcessor nettyProcessor;

    public LamdaContainer(LamdaConfig lamdaConfig) {
        this.lamdaConfig = lamdaConfig;
        init();
    }
    @Override
    public void init() {
        //容器启动
        //1. 构建核心处理器
        final NettyCoreProcessor nettyCoreProcessor = new NettyCoreProcessor();
        //2.判断是否开启缓存
        final String bufferType = lamdaConfig.getBufferType();

        if (LamdaBufferHelper.isFlusher(bufferType)) {
            nettyProcessor = new NettyBatchEventProcessor(lamdaConfig, nettyCoreProcessor);
        } else if (LamdaBufferHelper.isMpmc(bufferType)) {
            nettyProcessor = new NettyMpmcProcessor(lamdaConfig, nettyCoreProcessor);
        } else {
            nettyProcessor = new NettyCoreProcessor();
        }

        //3.创建NettyHttpServer
        nettyHttpServer = new NettyHttpServer(lamdaConfig, nettyProcessor);

        //4.创建NettyHttpClient
        nettyHttpClient = new NettyHttpClient(lamdaConfig, nettyHttpServer.getWorkerEventLoopGroup());
    }

    @Override
    public void start() {
        nettyHttpServer.start();
        nettyHttpClient.start();
        nettyProcessor.start();
        log.info("LamdaContainer started !");
    }

    @Override
    public void shutdown() {
        nettyHttpServer.shutdown();
        nettyHttpClient.shutdown();
        nettyProcessor.shutdown();
        log.info("LamdaContainer shutdown !");
    }
}
