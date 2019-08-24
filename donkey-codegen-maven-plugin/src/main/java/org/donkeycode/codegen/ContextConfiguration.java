package org.donkeycode.codegen;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 配置
 */
@Getter
@Setter
public class ContextConfiguration {

	private String author;
	private String packageName;
	private Path path;
	private Datasources datasources;
	private Map<String, TableConfiguration> tables;

	/**
	 * 数据源配置
	 */
	@Getter
	@Setter
	public static class Datasources {

		private String url;
		private String username;
		private String password;

		public Datasources() {
		}

		public Datasources(String url, String username, String password) {
			this.url = url;
			this.username = username;
			this.password = password;
		}

	}

	@Getter
	@Setter
	public static class Path {
		private String controller;
		private String service;
		private String interf;
		private String dao;
		private String entity;
		private String mapper;

		public Path() {
		}

		public Path(String controller, String service, String interf, String dao, String entity, String mapper) {
			this.controller = controller;
			this.service = service;
			this.interf = interf;
			this.dao = dao;
			this.entity = entity;
			this.mapper = mapper;
		}
	}

	@Getter
	@Setter
	public static class TableConfiguration {
		private String tableName;
		private String domainName;
		private String alias;
		private String description;
	}
}
