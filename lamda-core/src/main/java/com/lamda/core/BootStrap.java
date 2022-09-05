package com.lamda.core;

/**
 * lamda File Description
 * 网关入口启动类
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 09:14
 * @modify.date 2022-09-03 09:14
 * @since 0.1
 */
public class BootStrap {

    public static void main(String[] args) {
        //加载网关配置信息
        LamdaConfig config = LamdaConfigLoader.getInstance().load(args);
        System.out.println(config);

        //插件初始化工作

        //初始化服务注册中心

        //容器初始化
        final LamdaContainer lamdaContainer = new LamdaContainer(config);
        lamdaContainer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                lamdaContainer.shutdown();
            }
        }));
    }
}
