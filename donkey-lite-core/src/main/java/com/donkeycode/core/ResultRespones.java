package com.donkeycode.core;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
@ApiModel(description = "通用响应对象返回值")
public class ResultRespones<T> extends BaseEntity {

    @ApiModelProperty(value = "结果数据", position = 2)
    private T data;
    @ApiModelProperty(value = "消息说明", position = 1)
    public String message;
    @ApiModelProperty(value = "响应代码", position = 0)
    public int code = 200;

    public ResultRespones() {
    }

    public ResultRespones(T data) {
        this.data = data;
        this.message = null;
    }

    public static <T> ResultRespones<T> success(T data) {
        return new ResultRespones<T>(data);
    }

    public static <T> ResultRespones<T> fail(T data) {
        return new ResultRespones<T>(data);
    }
}
