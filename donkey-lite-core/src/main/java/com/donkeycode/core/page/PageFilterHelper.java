package com.donkeycode.core.page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.donkeycode.core.collection.CollectionUtils;
import com.donkeycode.core.utils.ColumnPropertyUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 分页插件实现
 *
 * @author yanjun.xue
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
    private List<String> orderBys;

    /**
     * 查询参会
     */
    private Map<String, String> queryParams;

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    /**
     * 前端排序字段转成SQL 排序语句
     *
     * @return
     */
    @Override
    public String getOrderBy() {
        if (CollectionUtils.isEmpty(orderBys)) {
            return "";
        }
        return orderBys.stream().map(s -> {
            if (StringUtils.isBlank(s)) {
                return "";
            }
            String[] singleOrder = s.split(":");
            return ColumnPropertyUtils.propertyToColumn(singleOrder[0]) + " " + (
                (singleOrder.length >= 2 &&
                    (ORDER_DESC.equalsIgnoreCase(singleOrder[1].trim()) || ORDER_ASC.equalsIgnoreCase(singleOrder[1].trim()))
                ) ? (singleOrder[1]) : (ORDER_ASC));
        }).collect(Collectors.joining());
    }

    @Override
    public void setNotQueryTotalNum(Boolean notQueryTotalNum) {
        this.notQueryTotalNum = notQueryTotalNum;
    }

}