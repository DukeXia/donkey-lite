package com.donkeycode.boot.pubinterface;

import com.donkeycode.boot.pubinterface.info.LinkedInfo;

/**
 * 联动数据修改
 *
 * @author donkey
 * @since 2018年9月19日
 */
public interface DataLinkedHandle {

    /**
     * 联动处理
     *
     * @param linkedInfo     联动消息
     * @param subOperateType 操作细分类别
     */
    LinkedInfo linkedHandle(LinkedInfo linkedInfo, String subOperateType);
}
