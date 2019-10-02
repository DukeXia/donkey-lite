package com.donkeycode.core;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ActionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Long createUserId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
    private Long lastUpdateUserId;

}
