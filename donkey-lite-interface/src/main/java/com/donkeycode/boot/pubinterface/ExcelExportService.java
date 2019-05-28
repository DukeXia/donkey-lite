package com.donkeycode.boot.pubinterface;

import java.util.List;
import java.util.Map;

/**
 * 导入Excel文件
 *
 * @param <T>
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
