package com.donkeycode.boot;

import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.donkeycode.boot.factory.FactoryManager;
import com.donkeycode.boot.pubinterface.DataLinkedHandle;
import com.donkeycode.boot.pubinterface.info.DataEditInfo;
import com.donkeycode.boot.pubinterface.info.LinkedInfo;
import com.donkeycode.boot.pubinterface.info.LogInfo;
import com.donkeycode.core.LiteConstants;
import com.donkeycode.core.utils.ObjectUtils;
import com.donkeycode.core.utils.JSONUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 修改资源接口管理
 *
 * @author donkey
 * @since 0.0.1
 */
@Slf4j
@Service
public class ResourceEditManager {

    @Autowired
    private FactoryManager factoryManager;

    /**
     * 新增资源
     *
     * @param resourceType
     * @param beanJson
     * @param operateType
     * @return
     */
    public LinkedInfo add(String resourceType, String beanJson, String operateType) {

        Validate.notBlank(resourceType, "resourceType is null.");

        if (!JSONUtils.isJson(beanJson)) {
            throw new IllegalArgumentException("接口参数beanjson非法数据格式，合法为Json格式的数据.");
        }
        log.debug("ResourceManager.add  资源类别：" + resourceType + "  DataJson:" + beanJson);
        LinkedInfo linkedInfo = factoryManager.getDataEditComponent(resourceType).insert(new DataEditInfo(beanJson, operateType));

        linkedInfo.setOperateType(operateType);
        linkedInfo.setResourceType(resourceType);

        linkedHandler(linkedInfo);
        recordLog(linkedInfo);
        return linkedInfo;
    }

    /**
     *
     * 记录日志
     *
     * @param linkedInfo
     */
    private void recordLog(LinkedInfo linkedInfo) {
        LogInfo logInfo = new LogInfo(linkedInfo.getResourceType(), linkedInfo.getMasterId(), linkedInfo.getOperateType(), linkedInfo.getMasterJson());
        factoryManager.getDataEditComponent(LiteConstants.RESOURCE_TYPE_ACTION_LOG).insert(new DataEditInfo(JSON.toJSONString(logInfo), linkedInfo.getOperateType()));
    }

    /**
     * 通过JSON对象修改资源
     *
     * @param resourceType
     * @param beanJson
     * @param operateType
     * @return
     */
    public LinkedInfo modify(String resourceType, String beanJson, String operateType) {
        Validate.notBlank(resourceType, "resourceType is null.");

        if (!JSONUtils.isJson(beanJson)) {
            throw new IllegalArgumentException("接口参数beanjson非法数据格式，合法为Json格式的数据.");
        }

        log.debug("ResourceManager.modify  资源类别：" + resourceType + "  DataJson:" + beanJson);
        LinkedInfo linkedInfo = factoryManager.getDataEditComponent(resourceType).update(new DataEditInfo(beanJson, operateType));

        linkedInfo.setResourceType(resourceType);
        linkedInfo.setOperateType(operateType);

        linkedHandler(linkedInfo);
        recordLog(linkedInfo);
        return linkedInfo;
    }

    /**
     * 通过主键删除资源
     *
     * @param resourceType
     * @param idsJson
     * @param operateType
     * @return
     */
    public LinkedInfo delete(String resourceType, String idsJson, String operateType) {
        Validate.notBlank(resourceType, "resourceType is null.");

        if (!JSONUtils.isJson(idsJson)) {
            throw new IllegalArgumentException("接口参数idsJson非法数据格式，合法为Json格式的数据.");
        }
        log.debug("ResourceManager.deletes  资源类别：" + resourceType + "  IDS:" + idsJson);
        LinkedInfo linked = factoryManager.getDataEditComponent(resourceType).delete(new DataEditInfo(idsJson, operateType));
        linked.setResourceType(resourceType);
        linked.setOperateType(operateType);

        linkedHandler(linked);

        recordLog(linked);
        return linked;
    }

    /**
     * @param linkedInfo
     */
    private void linkedHandler(LinkedInfo linkedInfo) {

        if (Objects.isNull(linkedInfo)) {
            return;
        }
        for (DataLinkedHandle dataLinkedHandle : factoryManager.getDataLinkedHandle(linkedInfo.getResourceType(), linkedInfo.getOperateType())) {
            LinkedInfo info = dataLinkedHandle.linkedHandle(linkedInfo, linkedInfo.getOperateType());
            if (ObjectUtils.isNotNull(info)) {
                linkedHandler(linkedInfo);
            }
        }
    }
}