package com.donkeycode.core;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用接口响应数据包，包含响应code、响应的数据包data、消息message说明
 *
 *
 * @author nanfeng
 * @date 2019年10月5日
 * @param <T>
 */
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

    public static <T> ResultRespones<T> success() {
        return success(null);
    }

    public static <T> ResultRespones<T> success(T data) {
        return success(data, null);
    }

    public static <T> ResultRespones<T> success(T data, String message) {
        ResultRespones<T> respones = new ResultRespones<T>(data);
        respones.setMessage(message);
        return respones;
    }

    public static <T> ResultRespones<T> fail() {
        return fail(500, "服务器内部错误");
    }

    public static <T> ResultRespones<T> fail(String message) {
        return fail(500, message);
    }

    public static <T> ResultRespones<T> fail(T data) {
        return fail(500, data);
    }

    public static <T> ResultRespones<T> fail(int code, T data) {
        return fail(code, data, null);
    }

    public static <T> ResultRespones<T> fail(int code, String message) {
        return fail(code, null, message);
    }

    public static <T> ResultRespones<T> fail(int code, T data, String message) {
        ResultRespones<T> respones = new ResultRespones<T>(data);
        respones.setMessage(message);
        respones.setCode(code);
        return respones;
    }
}
