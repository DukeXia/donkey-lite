package com.donkeycode.core.page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.donkeycode.core.utils.CollectionUtils;
import com.donkeycode.core.utils.ColumnPropertyUtils;
import com.donkeycode.core.utils.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页插件实现
 *
 * @author donkey
 * @since 2019年5月13日
 */

@Getter
@Setter
@Builder
public class PageFilterHelper implements PageFilter {

    public static final String ORDER_DESC = "desc";

    public static final String ORDER_ASC = "asc";

    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页显示条数
     */
    private int pageSize;

    private boolean notQueryTotalNum;

    /**
     * 排序字段
     */
    private List<String> orders;

    /**
     * 查询参会
     */
    private Map<String, String> params;

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * 前端排序字段转成SQL 排序语句
     *
     * @return
     */
    @Override
    public String getOrder() {
        if (CollectionUtils.isEmpty(orders)) {
            return "";
        }
        return orders.stream().map(item -> {
            if (StringUtils.isBlank(item)) {
                return "";
            }
            String[] singleOrderBy = item.trim().split(":");

            if (StringUtils.isBlank(singleOrderBy[0])) {
                return "";
            }

            String orderBy = singleOrderBy.length >= 2 ? singleOrderBy[1].trim().toLowerCase() : "";
            if (StringUtils.isNotBlank(orderBy) && (orderBy.startsWith(ORDER_DESC) || orderBy.startsWith(ORDER_ASC))) {
                orderBy = orderBy.startsWith(ORDER_DESC) ? ORDER_DESC : ORDER_ASC;
            } else {
                orderBy = ORDER_ASC;
            }
            return ColumnPropertyUtils.propertyToColumn(singleOrderBy[0]) + " " + orderBy;
        }).collect(Collectors.joining(", "));
    }

    @Override
    public void setNotQueryTotalNum(Boolean notQueryTotalNum) {
        this.notQueryTotalNum = notQueryTotalNum;
    }

    @Override
    public boolean isQueryTotalNum() {
        return !notQueryTotalNum;
    }

}
