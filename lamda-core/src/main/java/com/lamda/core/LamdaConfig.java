package com.lamda.core;

import com.lamda.common.constants.BasicConst;
import com.lamda.common.utils.NetUtils;
import lombok.Data;

/**
 * lamda File Description
 * 网关的通用配置信息类
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 09:17
 * @modify.date 2022-09-03 09:17
 * @since 0.1
 */
@Data
public class LamdaConfig {


    //网关的默认端口号
    private int port = 8888;

    //网关唯一ID
    private String rapidId = NetUtils.getLocalIp() + BasicConst.COLON_SEPARATOR + port;

    //网关注册地址
    private String registerAddress = "http://192.168.11.114:2379,http://192.168.11.115:2379,http://192.168.11.116:2379";

    //网关命名空间
    private String nameSpace = "lamda-dev";

    //网关服务器的CPU核心数映射的线程数
    private int processThread = Runtime.getRuntime().availableProcessors();

    //Netty的Boss线程数
    private int eventLoopGroupBossNum = 1;

    //Netty的Worker线程数
    private int eventLoopGroupWorkerNum = processThread;

    //是否开启EPOLL
    private boolean useEpoll = true;

    //是否开启Netty内存分配机制
    private boolean nettyAllocator = true;

    //HTTP Body报文最大大小
    private int maxContentLength = 64 * 1024 * 1024;

    //Dubbo开启连接数量
    private int dubboConnections = processThread;

    //设置响应模式，默认是单异步模式，CompletableFuture回调处理结果： whenComplete  or  whenCompleteAsync
    private boolean whenComplete = true;

    //TODO
    private String bufferType = null;//apidBufferHelper.MPMC; // RapidBufferHelper.FLUSHER;


    //	Http Async 参数选项：

    //	连接超时时间
    private int httpConnectTimeout = 30 * 1000;

    //	请求超时时间
    private int httpRequestTimeout = 30 * 1000;

    //	客户端请求重试次数
    private int httpMaxRequestRetry = 2;

    //	客户端请求最大连接数
    private int httpMaxConnections = 10000;

    //	客户端每个地址支持的最大连接数
    private int httpConnectionsPerHost = 8000;

    //	客户端空闲连接超时时间, 默认60秒
    private int httpPooledConnectionIdleTimeout = 60 * 1000;

}


