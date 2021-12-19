package com.cddx.common.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.cddx.common.core.constant.Constants;
import com.cddx.common.core.constant.SecurityConstants;
import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.web.response.AjaxResult;
import com.cddx.common.security.wrapper.DecryptRequestWrapper;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 请求解密
 *
 * @author 范劲松
 */
@Log4j2
@Component
@WebFilter(filterName = "requestDecryptFilter", urlPatterns = {"/*"})
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class RequestDecryptFilter implements Filter {

    @SuppressWarnings("all")
    private static final List<String> DEFAULT_WHITE = new ArrayList<>(Arrays.asList("/actuator/**"));

    @Resource
    private Config config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 不开启的放行
        if (!config.getEnable()) {
            // 放行
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        String method = request.getMethod();
        // 只解post和put
        if (!(HttpMethod.POST.name().equals(method)
                || HttpMethod.PUT.name().equals(method))) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 放行内部调用
        if (StringUtils.equals(SecurityConstants.INNER, request.getHeader(SecurityConstants.FROM_SOURCE))) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 白名单成员放行
        if (StringUtils.matches(request.getRequestURI(), DEFAULT_WHITE)
                || StringUtils.matches(request.getRequestURI(), config.getWhites())) {
            // 放行
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 解密参数处理
        DecryptRequestWrapper requestWrapper = new DecryptRequestWrapper(request);
        PrintWriter writer = null;
        try {
            // 处理
            requestWrapper.getParamsAndDecrypt();
            // 放行
            chain.doFilter(requestWrapper, servletResponse);
        } catch (Exception e) {
            servletResponse.setCharacterEncoding(Constants.UTF8);
            servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer = servletResponse.getWriter();
            ResultEnum responseEnum = ResultEnum.FIELD_VALIDATION_ERROR;
            writer.write(new JSONObject(AjaxResult.error(responseEnum)).toString());
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @ToString
    @Configuration
    @ConfigurationProperties("decrypt.request")
    static class Config {

        private List<String> whites = new ArrayList<>();

        private Boolean enable = false;

        public List<String> getWhites() {
            return whites;
        }

        public void setWhites(List<String> whites) {
            this.whites = whites;
        }

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }
    }
}
