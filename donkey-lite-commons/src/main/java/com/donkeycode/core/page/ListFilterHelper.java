package com.donkeycode.core.page;

import com.donkeycode.core.Constants;
import com.donkeycode.core.utils.ColumnPropertyUtils;
import com.donkeycode.core.utils.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 分页插件实现
 *
 * @author donkey
 * @since 2019年5月13日
 */

@Getter
@Setter
@Builder
public class ListFilterHelper implements ListFilter {


    private Constants.OrderMode orderMethod;

    private String orderField;

    /**
     * 查询参会
     */
    private Map<String, String> params;

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
    public String getOrderBy() {
        if (StringUtils.isEmpty(orderField)) {
            return "";
        }
        return ColumnPropertyUtils.propertyToColumn(orderField).concat(" "+orderMethod.getMode());
    }
}
