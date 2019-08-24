package org.donkeycode.codegen.utils;

import java.io.InputStream;
import java.net.URL;

import org.donkeycode.codegen.ContextConfiguration;
import org.yaml.snakeyaml.Yaml;


/**
 * Author GreedyStar
 * Date   2018/9/7
 */
public class ConfigUtil {
    private static ContextConfiguration contextConfiguration;

    static {
        URL url = ConfigUtil.class.getClassLoader().getResource("generator.yaml");
        if (url.getPath().contains("jar")) { // 用户未提供配置文件
            System.err.println("Can not find file named 'generator.yaml' at resources path, please make sure that you have defined that file.");
            System.exit(0);
        } else {
            InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream("generator.yaml");
            Yaml yaml = new Yaml();
            contextConfiguration = yaml.loadAs(inputStream, ContextConfiguration.class);
        }
    }

    public static ContextConfiguration getConfiguration() {
        return contextConfiguration;
    }

}
