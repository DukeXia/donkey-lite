package com.donkeycode.boot.pubinterface.info;

import com.donkeycode.core.BaseEntity;

/**
 * @author yanjun.xue
 */
public class DataEditInfo extends BaseEntity {

    private static final long serialVersionUID = -1;

    public final String editInfo;
    public final String operateType;

    public DataEditInfo(String editInfo) {
        this.editInfo = editInfo;
        this.operateType = null;
    }

    public DataEditInfo(String editInfo, String operateType) {
        this.editInfo = editInfo;
        this.operateType = operateType;
    }

    public DataEditInfo(String editInfo, String operateType, String contextPath) {
        this.editInfo = editInfo;
        this.operateType = operateType;
    }
}
