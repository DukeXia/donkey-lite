package com.donkeycode.boot.pubinterface.info;

import com.donkeycode.core.BaseEntity;

/**
 * Web修改接口对数据进行包装，方便业务层使用
 *
 * @author yanjun.xue
 * @since 0.0.1
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
