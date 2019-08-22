package com.donkeycode.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * 通用 Resource
 * 
 * @author yanjun.xue
 * @since 2019年6月27日
 * @param <ResourceService>
 * @param <Resource>
 */
public abstract class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@PostConstruct
	private void initPermCheck() {

	}

	public String getCurrentUserName() {
		return null;
	}

	public String getCurrentUserId() {
		return null;
	}
}
