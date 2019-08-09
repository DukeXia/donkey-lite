package com.donkeycode.core.page;

import java.util.List;
import java.util.Map;

/**
 * 分页接口定义
 *
 * @author donkey
 * @since 2019年5月13日
 */
public interface PageFilter {

    /**
     * 获取当前页
     *
     * @return
     */
    int getPageNum();


    /**
     * 设置是否查询总数量
     *
     * @param notQueryTotalNum
     */
    void setNotQueryTotalNum(Boolean notQueryTotalNum);
    
    
    /**
     * 设置是否查询总数量
     *
     */
    boolean isQueryTotalNum();

    /**
     * 获取每页中多少条
     *
     * @return
     */
    int getPageSize();

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
