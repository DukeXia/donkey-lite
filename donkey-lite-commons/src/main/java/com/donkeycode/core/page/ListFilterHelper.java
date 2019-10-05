package com.donkeycode.core.page;

import com.donkeycode.core.Constants;
import com.donkeycode.core.utils.ColumnPropertyUtils;
import com.donkeycode.core.utils.StringUtils;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("排序方式")
    private Constants.OrderMode orderMethod;
    @ApiModelProperty("排序字段")
    private String orderField;
    @ApiModelProperty("是否排序，默认值需要排序")
    private boolean isOrderBy = true;

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
        return ColumnPropertyUtils.propertyToColumn(orderField).concat(" " + orderMethod.getMode());
    }

    @Override
    public void setIsOrderBy(boolean isOrderBy) {
        this.isOrderBy = isOrderBy;
    }

    @Override
    public boolean isOrderBy() {
        return isOrderBy;
    }
}
