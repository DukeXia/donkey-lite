package com.donkeycode.boot.pubinterface;

import java.io.InputStream;
import java.util.Map;

/**
 * 
 * @author yanjun.xue
 * @since 2019年5月19日
 */
public interface ImageExportService {

	/**
	 * 
	 * @param params
	 * @return
	 */
	InputStream exportImage(Map<String, String> params);

}
