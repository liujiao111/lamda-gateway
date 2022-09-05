package com.lamda.core.netty;

import com.lamda.common.utils.RemotingUtil;
import com.lamda.core.LamdaConfig;
import com.lamda.core.LifeCycle;
import com.lamda.core.netty.peocessor.NettyProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * lamda File Description
 * 承接所有网络请求 的核心类
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 10:08
 * @modify.date 2022-09-03 10:08
 * @since 0.1
 */
@Slf4j
public class NettyHttpServer implements LifeCycle {

    private final LamdaConfig lamdaConfig;

    private int port = 8888;

    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossEventLoopGroup;

    private EventLoopGroup workerEventLoopGroup;

    private NettyProcessor nettyProcessor;

    public NettyHttpServer(LamdaConfig lamdaConfig, NettyProcessor nettyProcessor) {
        this.lamdaConfig = lamdaConfig;
        this.nettyProcessor = nettyProcessor;
        if(lamdaConfig.getPort() > 0 && lamdaConfig.getPort() < 65535) {
            this.port = lamdaConfig.getPort();
        }
        init();
    }

    @Override
    public void init() {
        this.serverBootstrap = new ServerBootstrap();
        //是否支持epoll
        if(userEpoll()) {
            this.bossEventLoopGroup = new EpollEventLoopGroup(lamdaConfig.getEventLoopGroupBossNum(),
                    new DefaultThreadFactory("NettyBossEpoll"));
            this.workerEventLoopGroup = new EpollEventLoopGroup(lamdaConfig.getEventLoopGroupWorkerNum(),
                    new DefaultThreadFactory("NettyWorkerEpoll"));
        } else {
            this.bossEventLoopGroup = new NioEventLoopGroup(lamdaConfig.getEventLoopGroupBossNum(),
                    new DefaultThreadFactory("NettyBossNIO"));
            this.workerEventLoopGroup = new NioEventLoopGroup(lamdaConfig.getEventLoopGroupWorkerNum(),
                    new DefaultThreadFactory("NettyWorkerNIO"));
        }
    }

    /**
     * 服务器启动模板
     */
    @Override
    public void start() {
        ServerBootstrap serverBootstrap = this.serverBootstrap
                .group(bossEventLoopGroup, workerEventLoopGroup)
                .channel(userEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true) //TCP端口重绑定 - 复用
                .option(ChannelOption.SO_KEEPALIVE, false) //如果在两小时内没有数据通信的时候，TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.TCP_NODELAY, true) //禁用Nagle算法
                .childOption(ChannelOption.SO_SNDBUF, 65535) //发送数据缓冲区大小
                .childOption(ChannelOption.SO_RCVBUF, 65535) //接收数据缓冲区大小
                .localAddress(new InetSocketAddress(this.port))
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(
                                new HttpServerCodec()
                                , new HttpObjectAggregator(lamdaConfig.getMaxContentLength())
                                , new HttpServerExpectContinueHandler()
                                , new NettyServerConnectManagerHandler()
                                , new NettyHttpServerHandler(nettyProcessor));
                    }
                });

        if(lamdaConfig.isNettyAllocator()) {
            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        }

        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            log.info("<============ Lamda Server startUp on port : {}" , this.port + "==========>");
        } catch (InterruptedException e) {
            throw new RuntimeException("this .serverbootstrap bind() sync failed!" ,e);
        }
    }

    @Override
    public void shutdown() {
        if(bossEventLoopGroup != null) {
            bossEventLoopGroup.shutdownGracefully();
        }
        if(workerEventLoopGroup != null) {
            workerEventLoopGroup.shutdownGracefully();
        }
    }

    /**
     * 是否支持epoll
     * @return
     */
    private boolean userEpoll() {
        return lamdaConfig.isUseEpoll() && RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
    }

    public EventLoopGroup getWorkerEventLoopGroup() {
        return workerEventLoopGroup;
    }
}


