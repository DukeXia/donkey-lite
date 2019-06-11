package com.donkeycode.core;

import java.time.Instant;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用登陆用户定义
 *
 * @author donkey
 * @since 2018年12月31日
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginUser extends BaseEntity {

    private String userId;
    private String userName;
    private Instant loginTime;
    /** 拥有角色*/
    private Collection<String> roleCodes;
    /** 操作权限*/
    private Collection<String> actionCodes;
}
