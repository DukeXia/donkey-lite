package com.donkeycode.boot.pubinterface.info;

import com.donkeycode.core.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yanjun.xue
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
public class LogInfo extends BaseEntity {

    private String resourceType;
    private String resourceIds;
    private String operateType;
    private String param;

    public LogInfo(String resourceType, String resourceIds, String operateType, String param) {
        this.resourceType = resourceType;
        this.resourceIds = resourceIds;
        this.operateType = operateType;
        this.param = param;
    }
}
