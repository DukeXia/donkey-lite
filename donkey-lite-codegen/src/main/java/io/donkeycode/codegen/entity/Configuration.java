package io.donkeycode.codegen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author GreedyStar
 * Date   2018/9/7
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    private String author;
    private String packageName;
    private Path path;
    private Db db;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Db {

        private String url;
        private String username;
        private String password;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Path {

        private String controller = "";
        private String commQueryBiz = "";
        private String commEditBiz = "";
        private String service = "";
        private String interf = "";
        private String entity = "";
        private String mapper = "";
        private String mapperXml = "";

    }
}
