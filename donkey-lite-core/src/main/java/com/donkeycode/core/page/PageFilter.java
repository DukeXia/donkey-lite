package com.donkeycode.core.page;

import java.util.Map;

/**
 * 分页接口定义
 *
 * @author yanjun.xue
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
    String getOrderBy();

    /**
     * 获取查询参数
     *
     * @return
     */
    Map<String, String> getQueryParams();
}