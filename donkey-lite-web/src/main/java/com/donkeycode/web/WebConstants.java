package com.donkeycode.web;

/**
 * 基础常量定义，推荐应用直接基础此类进行重新
 *
 * @author donkey
 * @since 0.0.1
 */
public class WebConstants {
    // token
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_BEARER = "Bearer ";
    public static final String AUTHORIZATION_TOKEN = "access_token";

    // 显示行数
    public static final String PAGE_SIZE = "pageSize";

    // 第几页
    public static final String PAGE_INDEX = "pageNo";

    // 默认显示行
    public static final int DEFAULT_PAGE_SIZE = 10;

    // 默认显示页
    public static final int DEFAULT_PAGE_INDEX = 0;

    // 分页总记录数据
    public static final String PAGE_TOTAL = "totalCount";

    // 列表数据
    public static final String PAGE_DATA = "data";

    // 是否支持缓存
    public static final String HEADER_IS_CACHE = "iscache";


}