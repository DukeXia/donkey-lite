package com.donkeycode.web;

import com.donkeycode.core.collections.SetList;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageFilterHelper;
import com.donkeycode.core.utils.CollectionUtils;
import com.donkeycode.core.utils.StringEncaseUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * 请求参数工具类
 *
 * @author csf
 * @Date 2015/7/27
 */
public class HttpServletParamUtils {

    public static String transcoding(String value) {
        return new String(value.getBytes(ISO_8859_1), UTF_8);
    }

    /**
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static String getPageOffset(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        return offset < 0 ? 0 + "" : offset + "";
    }

    /**
     * HttpServletRequest parameter to hashmap
     *
     * @param request
     * @return
     */
    public static HashMap<String, String> requestToMap(@NotBlank HttpServletRequest request) {
        HashMap<String, String> params = new HashMap<>();
        Enumeration<String> names = request.getParameterNames();
        Collections.list(names).stream().filter(name -> StringUtils.isNotBlank(name)).forEach(name -> {
            params.put(name, request.getParameter(name));
        });
        return params;
    }

    /**
     * 获取分页大小
     *
     * @param params
     * @return
     */
    public static int pageSize(Map<String, String> params) {
        if (CollectionUtils.isEmpty(params) || StringUtils.isEmpty(params.get(WebConstants.PAGE_SIZE))) {
            return WebConstants.DEFAULT_PAGE_SIZE;
        }
        return Integer.parseInt(params.get(WebConstants.PAGE_SIZE));
    }

    /**
     * 当前页号
     *
     * @param params
     * @return
     */
    public static int pageIndex(Map<String, String> params) {
        if (CollectionUtils.isEmpty(params) || StringUtils.isEmpty(params.get(WebConstants.PAGE_INDEX))) {
            return WebConstants.DEFAULT_PAGE_INDEX;
        }
        int pageIndex = Integer.parseInt(params.get(WebConstants.PAGE_INDEX));
        return pageIndex <= 0 ? 0 : pageIndex;
    }


    /**
     * 根据预订的规则获取分页参数
     *
     * @param request
     * @return
     */
    public static PageFilter pageFilter(@NotBlank HttpServletRequest request) {
        Map<String, String> params = HttpServletParamUtils.requestToMap(request);
        int pageNum = HttpServletParamUtils.pageIndex(params);
        int pageSize = HttpServletParamUtils.pageSize(params);
        PageFilter pageFilter = PageFilterHelper.builder().pageNum(pageNum).pageSize(pageSize).queryParams(params).build();

        if (StringEncaseUtils.isNotBlank(params.get("orderBys[]"))) {
            String[] orderBys = request.getParameterValues("orderBys[]");
            if (CollectionUtils.isNotEmpty(orderBys)) {
                SetList<String> orderBySet = new SetList();
                Stream.of(orderBys).forEach(item -> {
                    orderBySet.add(item.replace(" ", ""));
                });
                pageFilter.setOrderBys(orderBySet.stream().collect(Collectors.toList()));
            }
        }
        return pageFilter;
    }
}
