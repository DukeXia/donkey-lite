package com.donkeycode.boot.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.donkeycode.boot.annotation.AnnotationUtils;
import com.donkeycode.boot.annotation.LinkedHandler;
import com.donkeycode.boot.annotation.ResourceInfo;
import com.donkeycode.boot.pubinterface.DataEditService;
import com.donkeycode.boot.pubinterface.DataLinkedHandle;
import com.donkeycode.boot.pubinterface.DataQueryService;
import com.donkeycode.boot.pubinterface.FileExportService;
import com.donkeycode.boot.pubinterface.ImageExportService;
import com.donkeycode.boot.pubinterface.SelfDefinedSearch;
import com.donkeycode.core.collection.CollectionUtils;
import com.donkeycode.core.exception.FactoryException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("rawtypes")
@Service
public class FactoryManager {

    private final Map<String, DataEditService> editComponentMap;
    private final Map<String, DataQueryService> queryComponentMap;
    private final Map<String, Map<String, List<DataLinkedHandle>>> linkedHandleMap;
    private final Map<String, Map<String, SelfDefinedSearch>> selfDefinedSearchMap;
    private final Map<String, FileExportService> exportFileMap;
    private final Map<String, ImageExportService> exportImageFileMap;
    private final Map<String, String> nameDescriptionMap;

    public FactoryManager() {
        this.editComponentMap = new HashMap<>();
        this.nameDescriptionMap = new HashMap<>();
        this.queryComponentMap = new HashMap<>();
        this.selfDefinedSearchMap = new HashMap<>();
        this.linkedHandleMap = new HashMap<>();
        this.exportFileMap = new HashMap<>();
        this.exportImageFileMap = new HashMap<>();
    }

    /**
     * 初始化 数据修改 IDataEditComponent 实现类
     *
     * @param fileDownloadComponents 接口实现
     */
    @Autowired(required = false)
    private void init(FileExportService[] fileDownloadComponents) {
        if (CollectionUtils.isNotEmpty(fileDownloadComponents)) {
            for (FileExportService fileDownloadComponent : fileDownloadComponents) {
                ResourceInfo info = AnnotationUtils.getAnnotation(fileDownloadComponent, ResourceInfo.class);
                if (info != null) {
                    initResourceInfo(exportFileMap, info);
                    exportFileMap.put(info.type(), fileDownloadComponent);
                }
            }
        }
    }

    /**
     * 初始化 数据修改 IDataEditComponent 实现类
     *
     * @param fileComponents 接口实现
     */
    @Autowired(required = false)
    private void init(ImageExportService[] fileComponents) {
        if (CollectionUtils.isNotEmpty(fileComponents)) {
            for (ImageExportService fileComponent : fileComponents) {
                ResourceInfo info = AnnotationUtils.getAnnotation(fileComponent, ResourceInfo.class);
                if (info != null) {
                    initResourceInfo(exportImageFileMap, info);
                    exportImageFileMap.put(info.type(), fileComponent);
                }
            }
        }
    }

    /**
     * 初始化 数据修改 IDataEditComponent 实现类
     *
     * @param dataEditComponents 接口实现
     */
    @Autowired(required = false)
    private void init(DataEditService[] dataEditComponents) {
        if (CollectionUtils.isNotEmpty(dataEditComponents)) {
            for (DataEditService dataEditService : dataEditComponents) {
                // 获取注解信息
                ResourceInfo info = AnnotationUtils.getAnnotation(dataEditService, ResourceInfo.class);
                if (info != null) {
                    // 判断资源是否冲突
                    initResourceInfo(editComponentMap, info);
                    editComponentMap.put(info.type(), dataEditService);
                }
            }
        }
    }

    /**
     * 初始化 通用查询 IDataQueryComponent 实现类
     *
     * @param dataQueryComponents 接口实现
     */
    @Autowired(required = false)
    private void init(DataQueryService[] dataQueryComponents) {
        if (CollectionUtils.isNotEmpty(dataQueryComponents)) {
            for (DataQueryService dataQueryService : dataQueryComponents) {
                // 获取注解信息
                ResourceInfo info = AnnotationUtils.getAnnotation(dataQueryService, ResourceInfo.class);
                if (info != null) {
                    // 判断资源是否冲突
                    initResourceInfo(queryComponentMap, info);
                    queryComponentMap.put(info.type(), dataQueryService);
                }
            }
        }
    }

    /**
     * 初始化 联动修改接口
     *
     * @param dataLinkedHandles 接口实现
     */
    @Autowired(required = false)
    private void init(DataLinkedHandle[] dataLinkedHandles) {
        if (CollectionUtils.isNotEmpty(dataLinkedHandles)) {
            for (DataLinkedHandle dataLinkedHandle : dataLinkedHandles) {
                // 获取注解信息
                LinkedHandler linkedHandler = AnnotationUtils.getAnnotation(dataLinkedHandle, LinkedHandler.class);
                // 当对象和注解信息不为空
                if (dataLinkedHandle != null && linkedHandler != null) {
                    if (linkedHandleMap.containsKey(linkedHandler.dependName())) {
                        Map<String, List<DataLinkedHandle>> operateTypeListMap = linkedHandleMap.get(linkedHandler.dependName());
                        List<DataLinkedHandle> dataLinkedHandleList = operateTypeListMap.get(linkedHandler.getOperateType());
                        if (dataLinkedHandleList != null && !dataLinkedHandleList.isEmpty()) {
                            // 如果原类别Map存在此操作类别的列表，在列表中增加即可
                            dataLinkedHandleList.add(dataLinkedHandle);
                        } else {
                            // 如果原类别Map中不存在，新增加此类别列表
                            dataLinkedHandleList = new ArrayList<>();
                            dataLinkedHandleList.add(dataLinkedHandle);
                            operateTypeListMap.put(linkedHandler.getOperateType(), dataLinkedHandleList);
                        }
                    } else {
                        // 如果原资源类别Map中不存在，新增加此资源类别列表
                        Map<String, List<DataLinkedHandle>> operateTypeMap = new HashMap<>(1);
                        List<DataLinkedHandle> dataLinkedHandleList = new ArrayList<>();
                        dataLinkedHandleList.add(dataLinkedHandle);
                        operateTypeMap.put(linkedHandler.getOperateType(), dataLinkedHandleList);
                        linkedHandleMap.put(linkedHandler.dependName(), operateTypeMap);
                    }
                }
            }
        }
    }

    @Autowired(required = false)
    private void init(Map<String, SelfDefinedSearch> selfDefinedSearchs) {
        if (CollectionUtils.isNotEmpty(selfDefinedSearchs)) {
            for (Map.Entry<String, SelfDefinedSearch> entry : selfDefinedSearchs.entrySet()) {
                SelfDefinedSearch selfDefinedSearch = entry.getValue();
                String resourceType = getResourceType(selfDefinedSearch);
                String functionName = getFunctionName(selfDefinedSearch, entry.getKey());
                if (!this.selfDefinedSearchMap.containsKey(resourceType)) {
                    this.selfDefinedSearchMap.put(resourceType, new HashMap<>());
                }
                if (getSelfDefinedSearchMap(resourceType).containsKey(functionName)) {
                    throw new FactoryException("查询函数命名冲突");
                }
                getSelfDefinedSearchMap(resourceType).put(functionName, selfDefinedSearch);
            }
        }
    }

    /**
     * 判断资源是否冲突
     *
     * @param map  接口容器
     * @param info 资源注解
     */
    private synchronized void initResourceInfo(Map<String, ?> map, ResourceInfo info) {
        assert map != null && info != null && !StringUtils.isEmpty(info.type());
        if (map.containsKey(info.type())) {
            if (nameDescriptionMap.containsKey(info.type())) {
                log.error("资源：" + info.type() + "已存在 与（资源简介）：" + nameDescriptionMap.get(info.type()) + "冲突");
                throw new FactoryException("资源：" + info.type() + "已存在 与（资源简介）：" + nameDescriptionMap.get(info.type()) + "冲突");
            } else {
                nameDescriptionMap.put(info.type(), info.description());
                log.error("资源：" + info.type() + "已存在");
                throw new FactoryException("资源：" + info.type() + "已存在");
            }
        }
    }

    /**
     * 通过资源类别，获取资源数据修改实现类
     *
     * @param resourceType 资源类别
     * @return 资源数据修改实现类
     */
    public DataEditService getDataEditComponent(String resourceType) {
        Validate.notBlank(resourceType, "获取资源数据修改实现时 资源类别不能为空");

        if (!editComponentMap.containsKey(resourceType)) {
            throw new FactoryException("资源:" + resourceType + "数据修改接口未实现.");
        }
        log.debug("获取资源：" + resourceType + "数据修改接口");
        return editComponentMap.get(resourceType);
    }

    /**
     * 通过资源类别，获取资源数据查询实现类
     *
     * @param resourceType 资源类别
     * @return 资源数据查询实现类
     */
    public DataQueryService getDataQueryComponent(String resourceType) {
        Validate.notBlank(resourceType, "获取资源数据查询实现时 资源类别不能为空.");
        if (!queryComponentMap.containsKey(resourceType)) {
            throw new FactoryException("资源:" + resourceType + "查询接口未实现.");
        }
        log.debug("获取资源：" + resourceType + "查询接口");
        return queryComponentMap.get(resourceType);
    }

    /**
     * 通过资源类别，获取资源联动实现类
     *
     * @param resourceType 资源类别
     * @param operateType  操作类别
     * @return 联动接口实现列表
     */
    public List<DataLinkedHandle> getDataLinkedHandle(String resourceType, String operateType) {
        Validate.notBlank(resourceType, "获取资源联动实现时 资源类别不能为空");
        Validate.notNull(operateType, "获取资源联动实现时 资源类别不能为空");

        if (linkedHandleMap.containsKey(resourceType)) {
            Map<String, List<DataLinkedHandle>> operateTypeListMap = linkedHandleMap.get(resourceType);
            if (operateTypeListMap.containsKey(operateType)) {
                log.debug("获取资源：" + resourceType + "    操作类别：" + operateType + "  联动接口");
                return operateTypeListMap.get(operateType);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 获取方法名
     *
     * @param selfDefinedSearch   自定义查询实现类
     * @param defaultFunctionName 缺省方法名
     * @return 方法名
     */
    private String getFunctionName(SelfDefinedSearch selfDefinedSearch, String defaultFunctionName) {
        Validate.notNull(selfDefinedSearch, "ISelfDefinedSearch  实例不可为空");

        String functionName = AnnotationUtils.getFunctionName(selfDefinedSearch);
        if (StringUtils.isEmpty(functionName)) {
            functionName = defaultFunctionName;
        }
        Validate.notNull(functionName, "缺少FunctionName!");
        return functionName;
    }

    /**
     * 获取资源类别
     *
     * @param selfDefinedSearch 自定义查询实现类
     * @return 资源类别
     */
    private String getResourceType(SelfDefinedSearch selfDefinedSearch) {
        String resourceType = AnnotationUtils.getResourceType(selfDefinedSearch);
        Validate.notNull(resourceType, "缺少ResourceType!");
        return resourceType;
    }

    /**
     * 获取自定义查询实现类
     *
     * @param resourceType 资源类别
     * @param functionName 方法名
     * @return 查询实现类
     */
    public SelfDefinedSearch getSelfDefinedSearch(String resourceType, String functionName) {
        Map<String, SelfDefinedSearch> functorMap = getSelfDefinedSearchMap(resourceType);
        SelfDefinedSearch ret = functorMap.get(functionName);
        Validate.notNull(ret, "不存在资源:" + resourceType + "的查询函数:" + functionName);
        return ret;
    }

    /**
     * 获取自定义查询实现类
     *
     * @param resourceType 资源类别
     * @return 查询实现类
     */
    public FileExportService exportFile(String resourceType) {
        if (!exportFileMap.containsKey(resourceType)) {
            throw new FactoryException("资源:" + resourceType + "不存在 数据修改接口 实现");
        }
        log.debug("获取资源：" + resourceType + "数据修改接口");
        return exportFileMap.get(resourceType);
    }

    /**
     * @param resourceType
     * @return
     */
    public ImageExportService exportImageFile(String resourceType) {
        if (!exportImageFileMap.containsKey(resourceType)) {
            throw new FactoryException("资源:" + resourceType + "不存在 数据修改接口 实现");
        }
        log.debug("获取资源：" + resourceType + "数据修改接口");
        return exportImageFileMap.get(resourceType);
    }

    /**
     * @param resourceType
     * @return
     */
    private Map<String, SelfDefinedSearch> getSelfDefinedSearchMap(String resourceType) {
        Map<String, SelfDefinedSearch> ret = selfDefinedSearchMap.get(resourceType);
        Validate.notNull(ret, "不存在资源:" + resourceType + "的查询函数");
        return ret;
    }
}