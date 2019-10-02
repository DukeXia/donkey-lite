package com.donkeycode.web;

import com.donkeycode.core.Constants;
import com.donkeycode.core.collectors.CollectorUtils;
import com.donkeycode.core.page.ListFilter;
import com.donkeycode.core.page.ListFilterHelper;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageFilterHelper;
import com.donkeycode.core.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * 请求参数工具类
 *
 * @author csf
 * @Date 2015/7/27
 */
public class HttpParamUtils {

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
        if (CollectorUtils.isEmpty(params) || StringUtils.isEmpty(params.get(PageFilter.PAGE_SIZE))) {
            return Constants.DEFAULT_PAGE_SIZE;
        }
        return Integer.parseInt(params.get(PageFilter.PAGE_SIZE));
    }

    /**
     * 当前页号
     *
     * @param params
     * @return
     */
    public static int pageNo(Map<String, String> params) {
        if (CollectorUtils.isEmpty(params) || StringUtils.isEmpty(params.get(PageFilter.PAGE_NO))) {
            return Constants.DEFAULT_PAGE_INDEX;
        }
        int pageNo = Integer.parseInt(params.get(PageFilter.PAGE_NO));
        return pageNo <= 0 ? 0 : pageNo;
    }


    /**
     * 根据预订的规则获取分页参数
     * eg:
     * http://localhost:9020/api/role/list?sortField=roleName&amp;sortOrder=descend&amp;orderBys[]=roleName:descend
     *
     * @param request
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ListFilter listFilter(@NotBlank HttpServletRequest request) {
        Map<String, String> params = HttpParamUtils.requestToMap(request);
        ListFilter listFilter = ListFilterHelper.builder()
            .params(params)
            .build();

        if (StringUtils.isNotBlank(params.get(Constants.ORDER_BY_KEY))) {
            listFilter.setOrderField(params.get(Constants.ORDER_BY_KEY));
            listFilter.setOrderMethod(Constants.OrderMode.valueCode(Constants.ORDER_METHOD_KEY));

        }
        return listFilter;
    }

    /**
     * 根据预订的规则获取分页参数
     * eg:
     * http://localhost:9020/api/role/page?pageNo=1&amp;pageSize=10&amp;sortField=roleName&amp;sortOrder=descend&amp;orderBys[]=roleName:descend
     *
     * @param request
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static PageFilter pageFilter(@NotBlank HttpServletRequest request) {
        Map<String, String> params = HttpParamUtils.requestToMap(request);
        int pageNo = HttpParamUtils.pageNo(params);
        int pageSize = HttpParamUtils.pageSize(params);

        PageFilter pageFilter = PageFilterHelper.builder()
            .pageNo(pageNo)
            .pageSize(pageSize)
            .params(params)
            .build();

        if (StringUtils.isNotBlank(params.get(Constants.ORDER_BY_KEY))) {
            pageFilter.setOrderField(params.get(Constants.ORDER_BY_KEY));
            pageFilter.setOrderMethod(Constants.OrderMode.valueCode(Constants.ORDER_METHOD_KEY));

        }
        return pageFilter;
    }
}
