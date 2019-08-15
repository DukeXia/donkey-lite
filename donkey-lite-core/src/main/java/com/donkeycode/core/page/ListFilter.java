package com.donkeycode.core.page;

import java.util.List;
import java.util.Map;

/**
 * 列表接口过滤器定义
 *
 * @author donkey
 * @since 2019年5月13日
 */
public interface ListFilter {

    /**
     * 获取排序字符串
     *
     * @return
     */
    String getOrder();

    /**
     * 获取排序字符串
     *
     * @return
     */
    void setOrders(List<String> orderBys);

    /**
     * 获取查询参数
     *
     * @return
     */
    Map<String, String> getParams();
}
