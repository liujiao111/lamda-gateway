package com.lamda.core;

import com.lamda.common.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * lamda File Description
 * 网关配置加载类
 * 配置加载规则顺序：（高优先级配置会覆盖低优先级的配置）
 * 运行参数->JVM参数->环境变量->配置文件->内部默认初始化属性值
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 09:43
 * @modify.date 2022-09-03 09:43
 * @since 0.1
 */
@Slf4j
public class LamdaConfigLoader {

    private final static String CONFIG_FILE = "lamda.properties";

    private final static String LAMDA_CONFIG_ENV_PREFIX = "lamda_";
    private final static String LAMDA_CONFIG_JVM_PREFIX = "lamda_";

    private final static LamdaConfigLoader INSTANCE = new LamdaConfigLoader();

    private LamdaConfig lamdaConfig = new LamdaConfig();

    private LamdaConfigLoader() {

    }

    public static LamdaConfigLoader getInstance() {
        return INSTANCE;
    }

    public static LamdaConfig getConfig() {
        return INSTANCE.lamdaConfig;
    }

    /**
     * 加载配置文件-根据顺序加载
     * @param args
     * @return
     */
    public LamdaConfig load(String args[]) {

        //加载配置数据

        //1、配置文件
        {
            InputStream resourceAsStream = LamdaConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
            if (resourceAsStream != null) {
                try {
                    Properties properties = new Properties();
                    properties.load(resourceAsStream);
                    PropertiesUtils.properties2Object(properties, lamdaConfig);
                } catch (IOException e) {
                    log.warn("#LamdaConfigLoader# load file {} is error, ", CONFIG_FILE, e);
                } finally {
                    if (resourceAsStream != null) {
                        try {
                            resourceAsStream.close();
                        } catch (IOException e) {
                            //忽略即可
                        }
                    }
                }
            }
        }


        //2、环境变量

        {
            Map<String, String> env = System.getenv();
            Properties properties = new Properties();
            properties.putAll(env);
            PropertiesUtils.properties2Object(properties, lamdaConfig, LAMDA_CONFIG_ENV_PREFIX);
        }

        //3、JVM参数
        {
            Properties properties = System.getProperties();
            PropertiesUtils.properties2Object(properties, lamdaConfig, LAMDA_CONFIG_JVM_PREFIX);

        }
        //4、运行参数 :例如：--enable = true
        {
            if (args != null && args.length > 0) {
                Properties properties = new Properties();
                for (String arg : args) {
                    if (arg.startsWith("--") && arg.contains("=")) {
                        properties.put(arg.substring(2, arg.indexOf("=")), arg.substring(arg.indexOf("=") + 1));
                        PropertiesUtils.properties2Object(properties, lamdaConfig);
                    }
                }
            }
        }

        return lamdaConfig;
    }


}
