package com.donkeycode.boot.pubinterface;

import java.util.List;
import java.util.Map;

/**
 * 导入Excel文件接口定义
 *
 * @param <T>
 * @author doneky
 *
 * @since 0.0.1
 */
public interface ExcelExportService<T> {

    /**
     * Excel 文件的信息
     *
     * @param params
     * @return
     */
    List<?> queryResource(Map<String, String> params);

    /**
     * Excel 列头信息
     *
     * @return
     */
    T getExcelHeader();
}
