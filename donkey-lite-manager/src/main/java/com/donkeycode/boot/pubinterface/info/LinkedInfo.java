package com.donkeycode.boot.pubinterface.info;

import com.donkeycode.core.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 操作返回前端数据Message
 *
 * @author donkey
 * @since 0.0.1
 */
@Setter
@Getter
public class LinkedInfo extends BaseEntity {

    private static final long serialVersionUID = -1;

    /**
     * 资源类别
     */
    private String resourceType;
    /**
     * 操作类别
     */
    private String operateType;
    /**
     * 操作对象ID
     */
    private String masterId;
    /**
     * 操作对象串（返回时，需要与联动接口约定）
     */
    private String masterJson;
    /**
     * 提醒消息
     */
    public String message;

    /**
     * 返回Code码
     */
    public int code = 200;

    /**
     * @param resourceType
     * @param operateType
     * @param masterId
     * @param masterJson
     */
    public LinkedInfo(String resourceType, String operateType, String masterId, String masterJson) {
        this.resourceType = resourceType;
        this.operateType = operateType;
        this.masterId = masterId;
        this.masterJson = masterJson;
        this.message = null;
    }


    /**
     * @param masterId
     * @param masterJson
     */
    public LinkedInfo(String masterId, String masterJson) {
        this.masterId = masterId;
        this.masterJson = masterJson;
    }


    /**
     * @param resourceType
     * @param operateType
     * @param masterId
     * @param masterJson
     * @param message
     */
    public LinkedInfo(String resourceType, String operateType, String masterId, String masterJson, String message) {
        this.resourceType = resourceType;
        this.operateType = operateType;
        this.masterId = masterId;
        this.masterJson = masterJson;
        this.message = message;
    }
}
