package org.donkeycode.codegen.application;

import java.util.ArrayList;
import java.util.List;

import org.donkeycode.codegen.api.MyBatisGenerator;
import org.donkeycode.codegen.config.Configuration;
import org.donkeycode.codegen.config.xml.ConfigurationParser;
import org.donkeycode.codegen.internal.DefaultShellCallback;
import org.springframework.cglib.util.StringSwitcher;

public class MybatisMapperGenerator {

    public static void main(String[] args) throws Exception {
        generator();
    }

    private static void generator() throws Exception {

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(StringSwitcher.Generator.class.getResourceAsStream("/generator/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("代码生成完毕>>>>>>>>>>>>");
    }

}
