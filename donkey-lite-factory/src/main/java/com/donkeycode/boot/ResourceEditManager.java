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
import com.donkeycode.core.utils.StringJsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 修改资源接口管理
 *
 * @author yanjun.xue
 * @since 0.0.1
 */
@Slf4j
@Service
public class ResourceEditManager {

    @Autowired
    private FactoryManager factoryManager;

    /**
     * @param resourceType
     * @param beanJson
     * @param operateType
     * @return
     */
    public String add(String resourceType, String beanJson, String operateType) {

        Validate.notBlank(resourceType, "resourceType is null.");

        if (!StringJsonUtils.isGoodJson(beanJson)) {
            throw new IllegalArgumentException("接口参数beanjson非法数据格式，合法为Json格式的数据.");
        }
        log.debug("ResourceManager.add  资源类别：" + resourceType + "  DataJson:" + beanJson);
        LinkedInfo linkedInfo = factoryManager.getDataEditComponent(resourceType).insert(new DataEditInfo(beanJson, operateType));

        linkedInfo.setOperateType(operateType);
        linkedInfo.setResourceType(resourceType);

        linkedHandler(linkedInfo);
        recordLog(linkedInfo);
        return linkedInfo.getMasterId();
    }

    /**
     * @param linkedInfo
     */
    private void recordLog(LinkedInfo linkedInfo) {
        LogInfo logInfo = new LogInfo(linkedInfo.getResourceType(), linkedInfo.getMasterId(), linkedInfo.getOperateType(), linkedInfo.getMasterJson());
        factoryManager.getDataEditComponent(LiteConstants.RESOURCE_TYPE_OPERATE_LOG).insert(new DataEditInfo(JSON.toJSONString(logInfo), linkedInfo.getOperateType()));
    }

    /**
     * @param resourceType
     * @param beanJson
     * @param operateType
     * @return
     */
    public String modify(String resourceType, String beanJson, String operateType) {
        Validate.notBlank(resourceType, "resourceType is null.");

        if (!StringJsonUtils.isGoodJson(beanJson)) {
            throw new IllegalArgumentException("接口参数beanjson非法数据格式，合法为Json格式的数据.");
        }

        log.debug("ResourceManager.modify  资源类别：" + resourceType + "  DataJson:" + beanJson);
        LinkedInfo linkedInfo = factoryManager.getDataEditComponent(resourceType).update(new DataEditInfo(beanJson, operateType));

        linkedInfo.setResourceType(resourceType);
        linkedInfo.setOperateType(operateType);

        linkedHandler(linkedInfo);
        recordLog(linkedInfo);
        return linkedInfo.getMasterId();
    }

    /**
     * @param resourceType
     * @param idsJson
     * @param userId
     * @param operateType
     * @return
     */
    public String delete(String resourceType, String idsJson, String userId, String operateType) {
        Validate.notBlank(resourceType, "resourceType is null.");

        if (!StringJsonUtils.isGoodJson(idsJson)) {
            throw new IllegalArgumentException("接口参数idsJson非法数据格式，合法为Json格式的数据.");
        }
        log.debug("ResourceManager.deletes  资源类别：" + resourceType + "  IDS:" + idsJson);
        LinkedInfo linked = factoryManager.getDataEditComponent(resourceType).delete(new DataEditInfo(userId, idsJson, operateType));
        linked.setResourceType(resourceType);
        linked.setOperateType(operateType);

        linkedHandler(linked);

        recordLog(linked);
        return linked.message;
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