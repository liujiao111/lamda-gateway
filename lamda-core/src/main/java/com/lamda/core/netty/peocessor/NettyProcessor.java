package com.lamda.core.netty.peocessor;

import com.lamda.core.netty.HttpRequestWrapper;

/**
 * lamda File Description
 * 处理Netty核心逻辑的处理器接口定义
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 15:35
 * @modify.date 2022-09-03 15:35
 * @since 0.1
 */
public interface NettyProcessor {

    /**
     * 处理请求核心方法
     * @param requestWrapper
     */
    void process(HttpRequestWrapper requestWrapper);

    /**
     * 执行器启动
     */
    void start();

    /**
     * 关闭释放资源
     */
    void shutdown();
}
