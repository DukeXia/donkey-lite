package com.donkeycode.boot;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.donkeycode.boot.factory.FactoryManager;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 资源查询接口管理
 *
 * @author yanjun.xue
 * @since 0.0.1
 */
@Slf4j
@Service
public class ResourceQueryManager {

    public static final String PARAM_MESSAGE = "参数不可为空";
    public static final String RESOURCE_TYPE_MESSAGE = "资源类别不可为空";

    @Autowired
    FactoryManager factoryManager;

    /**
     * 以列表的方式获取资源
     *
     * @param resourceType
     * @param params
     * @return
     */
    public List<?> list(String resourceType, String operateType, Map<String, String> params) {
        Validate.notBlank(resourceType, RESOURCE_TYPE_MESSAGE);
        Validate.notNull(params, PARAM_MESSAGE);

        log.debug("ResourceManager.list  资源类别：" + resourceType + "  params:" + JSON.toJSONString(params));
        return factoryManager.getDataQueryComponent(resourceType).list(operateType, params);
    }

    /**
     * 通过分页方式获取资源
     *
     * @param resourceType
     * @param operateType
     * @param pageFilter
     * @return 返回资源
     */
    public PageResult<?> pageList(String resourceType, String operateType, PageFilter pageFilter) {

        Validate.notBlank(resourceType, "resourceType is null.");
        return factoryManager.getDataQueryComponent(resourceType).pageList(operateType, pageFilter);
    }

    /**
     * 通过Id获取资源
     *
     * @param resourceType
     * @param id
     * @param params
     * @return
     */
    public Object find(String resourceType, String operateType, String id, Map<String, String> params) {

        Validate.notBlank(resourceType, "resourceType is null.");

        log.debug("ResourceManager.find  资源类别：" + resourceType + "  ID:" + id);
        return factoryManager.getDataQueryComponent(resourceType).find(operateType, id, params);
    }

    /**
     * 搜索资源
     *
     * @param resourceType
     * @param functionName
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object search(String resourceType, String functionName, Map<String, String> params) {

        Validate.notBlank(resourceType, "resourceType is null.");

        return factoryManager.getSelfDefinedSearch(resourceType, functionName).search(params);
    }
}