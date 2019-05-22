package com.donkeycode.boot.pubinterface;

import java.util.Map;

/**
 * 
 * @author yanjun.xue
 * @since 2019年5月19日
 * @param <T>
 */
public interface SelfDefinedSearch<T> {

	/**
	 * 
	 * @param condition
	 * @return
	 */
	T search(Map<String, String> condition);
}
