package com.lamda.core;

/**
 * lamda File Description
 *生命周期管理接口
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 10:05
 * @modify.date 2022-09-03 10:05
 * @since 0.1
 */
public interface LifeCycle {

    /**
     * 生命周期组件初始化
     */
    void init();
    /**
     * 生命周期组件启动
     */
    void start();

    /**
     * 生命周期组件关闭
     */
    void shutdown();

}
