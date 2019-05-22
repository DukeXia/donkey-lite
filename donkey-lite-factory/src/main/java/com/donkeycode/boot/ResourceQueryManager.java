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

@Slf4j
@Service
public class ResourceQueryManager {

    static final String PARAM_MESSAGE = "参数不可为空";
    static final String RESOURCETYPE_MESSAGE = "资源类别不可为空";

    @Autowired
    FactoryManager factoryManager;

    /**
     * @param resourceType
     * @param param
     * @return
     */
    public List<?> list(String resourceType, String operateType, Map<String, String> param) {
        Validate.notBlank(resourceType, RESOURCETYPE_MESSAGE);
        Validate.notNull(param, PARAM_MESSAGE);

        log.debug("ResourceManager.list  资源类别：" + resourceType + "  param:" + JSON.toJSONString(param));
        return factoryManager.getDataQueryComponent(resourceType).list(operateType, param);
    }

    /**
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
     * @param resourceType
     * @param id
     * @param parameterMap
     * @return
     */
    public Object find(String resourceType, String operateType, String id, Map<String, String> parameterMap) {

        Validate.notBlank(resourceType, "resourceType is null.");

        log.debug("ResourceManager.find  资源类别：" + resourceType + "  ID:" + id);
        return factoryManager.getDataQueryComponent(resourceType).find(operateType, id, parameterMap);
    }

    /**
     * @param resourceType
     * @param functionName
     * @param parameterMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object search(String resourceType, String functionName, Map<String, String> parameterMap) {

        Validate.notBlank(resourceType, "resourceType is null.");

        return factoryManager.getSelfDefinedSearch(resourceType, functionName).search(parameterMap);
    }
}