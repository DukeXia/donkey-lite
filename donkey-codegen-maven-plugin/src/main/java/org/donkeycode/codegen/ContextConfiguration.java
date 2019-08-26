package org.donkeycode.codegen;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 配置
 */
@Getter
@Setter
public class ContextConfiguration {

	private String author;
	private ControllerGenerator controllerGenerator;
	private ServiceGenerator serviceGenerator;
	private DataGenerator dataGenerator;
	private Datasources datasources;
	private Map<String, Table> tables;

	/**
	 * 数据源配置
	 */
	@Getter
	@Setter
    @AllArgsConstructor
    @NoArgsConstructor
	public static class Datasources {

		private String url;
		private String username;
		private String password;

	}

	@Getter
	@Setter
    @AllArgsConstructor
    @NoArgsConstructor
	public static class ControllerGenerator {
		private String targetPackage;
		private String targetProject;
	}

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceGenerator {
        private String targetPackage;
        private String targetProject;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataGenerator {
        private String targetPackage;
        private String targetProject;


        public  String getModelPackage(){
            return targetPackage.concat(".domain");
        }

        public String getMapperPackage(){
            return targetPackage.concat(".mapper");
        }
    }


    @Getter
	@Setter
    @AllArgsConstructor
    @NoArgsConstructor
	public static class Table {
		private String tableName;
		private String domainName;
		private String alias;
		private String description;
	}
}
