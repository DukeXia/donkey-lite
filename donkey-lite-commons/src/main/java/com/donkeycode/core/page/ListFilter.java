package com.donkeycode.core.page;

import com.donkeycode.core.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    String getOrderBy();

    /**
     * 设置是否排序
     *
     * @return
     */
    void setOrderBy(boolean isOrderBy);

    /**
     * 是否排序
     *
     * @return
     */
    boolean isOrderBy();

    /**
     * 设置排序字段
     *
     * @return
     */
    void setOrderField(@NotBlank String orderField);

    /**
     * 设置排序方式
     *
     * @param orderMode
     */
    void setOrderMethod(@NotNull Constants.OrderMode orderMode);

    /**
     * 获取查询参数
     *
     * @return
     */
    Map<String, String> getParams();
}
