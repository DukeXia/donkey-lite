package com.donkeycode.boot.pubinterface;

import java.io.InputStream;
import java.util.Map;

/**
 * 导出文件
 *
 * @author donkey
 */
public interface FileExportService {

    /**
     * @param params
     * @return
     */
    InputStream exportFile(Map<String, String> params);

}
