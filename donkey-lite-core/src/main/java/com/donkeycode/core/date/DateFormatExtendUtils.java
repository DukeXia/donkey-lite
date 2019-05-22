package com.donkeycode.core.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * @author yanjun.xue
 */
public class DateFormatExtendUtils extends DateFormatUtils {

    public static final FastDateFormat ISO_SIMPLIFY_TIME_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

    public static final FastDateFormat ISO_DATETIME_FORMAT_24 = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

}
