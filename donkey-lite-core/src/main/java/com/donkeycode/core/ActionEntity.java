package com.donkeycode.core;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ActionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Date createTime;
    private Long createUserId;
    private Date lastUpdateTime;
    private Long lastUpdateUserId;

}
