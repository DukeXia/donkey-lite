package com.donkeycode.core;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 基础实体类
 * 
 * 实现了Serializable接口，并且重写了ToString方法
 *
 * @author nanfeng
 * @date 2019年10月5日
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
