package org.donkeycode.codegen.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.donkeycode.codegen.ContextConfiguration;
import org.yaml.snakeyaml.Yaml;

/**
 * @author nanfeng
 * @data 2019年8月29日
 */
public class ConfigurationParser {

    private static ContextConfiguration contextConfiguration;

    private ContextConfiguration parseConfiguration(FileReader fr) {
        Yaml yaml = new Yaml();
        return contextConfiguration = yaml.loadAs(fr, ContextConfiguration.class);
    }

    public ContextConfiguration parseConfiguration(File inputFile) throws IOException {
        FileReader fr = new FileReader(inputFile);
        return parseConfiguration(fr);
    }

    public ContextConfiguration getConfiguration() {
        return contextConfiguration;
    }
}
