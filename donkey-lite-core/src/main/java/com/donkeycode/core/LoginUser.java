package com.donkeycode.core;

import java.time.Instant;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanjun.xue
 * @since 2018年12月31日
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginUser extends BaseEntity {

    private String userId;
    private String userName;
    private Instant loginTime;
    /*角色*/
    private Collection<String> roleCodes;
    /*资源*/
    private Collection<String> actionCodes;
}
