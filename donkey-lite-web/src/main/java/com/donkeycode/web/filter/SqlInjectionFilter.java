package com.donkeycode.web.filter;

import com.donkeycode.core.exception.ArgumentException;
import com.donkeycode.core.utils.CheckSqlInjectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * 防止SQL注入
 */
@Component
public class SqlInjectionFilter implements Filter {

    /**
     * 初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Iterator<?> values = req.getParameterMap().values().iterator();
        if (HttpMethod.valueOf(req.getMethod()).equals(HttpMethod.GET)) {
            if (CheckSqlInjectionUtils.isSqlInjection(req.getRequestURI())) {
                throw new ArgumentException("URL错误");
            }
        }
        while (values.hasNext()) {
            Stream.of((String[]) values.next()).forEach(e -> {
                if (CheckSqlInjectionUtils.isSqlInjection(e)) {
                    throw new ArgumentException("非法参数字符.");
                }
            });
        }
        chain.doFilter(request, response);
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }
}
