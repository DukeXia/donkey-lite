package com.donkeycode.core;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ActionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Instant createTime;
    private Long createUserId;
    private Instant lastUpdateTime;
    private Long lastUpdateUserId;

}
