package com.donkeycode.boot.pubinterface;

import java.util.Map;

/**
 * @param <T>
 * @author yanjun.xue
 * @since 2019年5月19日
 */
public interface SelfDefinedSearch<T> {

    /**
     * @param condition
     * @return
     */
    T search(Map<String, String> condition);
}
