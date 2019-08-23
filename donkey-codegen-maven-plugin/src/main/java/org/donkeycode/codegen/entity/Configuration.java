package org.donkeycode.codegen.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author nanfeng
 *
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class Configuration implements Serializable {

	private String author;
	private String packageName;
	private Path path;
	private Db db;

	@Getter
	@Setter
	public static class Db {
		private String url;
		private String username;
		private String password;

		public Db() {
		}

		public Db(String url, String username, String password) {
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
}
