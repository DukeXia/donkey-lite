package com.donkeycode.boot.pubinterface;

import java.util.List;
import java.util.Map;

import com.donkeycode.core.exception.FunctUnrealizedException;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageResult;

/**
 * @author donkey
 * @since 2019年5月19日
 */
public interface DataQueryService {

    String DEFAULT_MESSAGE_KEY = "未实现该功能.";

    /**
     * @param operateType
     * @param params
     * @return
     */
    default List<?> list(String operateType, Map<String, String> params) {
        throw new FunctUnrealizedException(DEFAULT_MESSAGE_KEY);
    }

    /**
     * @param operateType
     * @param pageFilter
     * @return
     */
    default PageResult<?> pageList(String operateType, PageFilter pageFilter) {
        throw new FunctUnrealizedException(DEFAULT_MESSAGE_KEY);
    }

    /**
     * @param operateType
     * @param key
     * @param params
     * @return
     */
    default Object find(String operateType, String key, Map<String, String> params) {
        throw new FunctUnrealizedException(DEFAULT_MESSAGE_KEY);
    }
}