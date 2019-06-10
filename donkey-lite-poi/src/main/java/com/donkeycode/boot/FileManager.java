package com.donkeycode.boot;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donkeycode.boot.factory.FactoryManager;
import com.donkeycode.boot.file.ExcelHead;
import com.donkeycode.boot.file.ExcelHelper;
import com.donkeycode.boot.pubinterface.DataQueryService;
import com.donkeycode.boot.pubinterface.ExcelExportService;
import com.donkeycode.boot.pubinterface.FileExportService;
import com.donkeycode.core.utils.CollectionUtils;

/**
 * Excel 文件导入导出
 *
 * @author yanjun.xue
 * @since 2018年12月9日
 */
@Service
public class FileManager {

    @Autowired
    FactoryManager factoryManager;

    /**
     * 导出Excel文件
     *
     * @param resourceType 资源标识符
     * @param parameterMap 过滤参数
     * @return
     */
    public Workbook exportResource(String resourceType, Map<String, String> parameterMap) {

        Objects.requireNonNull(resourceType, "resourceType is null.");

        DataQueryService dataQueryService = factoryManager.getDataQueryComponent(resourceType);
        if (dataQueryService instanceof ExcelExportService) {
            Object excelHeader = ((ExcelExportService<?>) dataQueryService).getExcelHeader();
            List<?> objects = ((ExcelExportService<?>) dataQueryService).queryResource(parameterMap);
            if (CollectionUtils.isNotEmpty(objects)) {
                return ExcelHelper.exportExcelFile((ExcelHead) excelHeader, objects);
            }
        }
        return null;
    }

    /**
     * @param resourceType
     * @return
     */
    public InputStream exportFile(String resourceType, Map<String, String> params) {

        Objects.requireNonNull(resourceType, "resourceType is null.");

        FileExportService fileComponent = factoryManager.exportFile(resourceType);
        if (fileComponent instanceof FileExportService) {
            return fileComponent.exportFile(params);
        }
        return null;
    }
}