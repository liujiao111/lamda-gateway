package com.lamda.core.netty.peocessor;

import com.lamda.core.LamdaConfig;
import com.lamda.core.netty.HttpRequestWrapper;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 16:09
 * @modify.date 2022-09-03 16:09
 * @since 0.1
 */
public class NettyMpmcProcessor implements NettyProcessor {


    private LamdaConfig lamdaConfig;

    private NettyCoreProcessor nettyCoreProcessor;

    public NettyMpmcProcessor (LamdaConfig lamdaConfig, NettyCoreProcessor nettyCoreProcessor) {
        this.lamdaConfig = lamdaConfig;
        this.nettyCoreProcessor = nettyCoreProcessor;
    }

    @Override
    public void process(HttpRequestWrapper requestWrapper) {

    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }
}
