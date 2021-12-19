package com.cddx.common.security.filter;

import com.cddx.common.security.wrapper.RequestWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 拦截所有请求过滤器，并将请求类型是HttpServletRequest类型的请求替换为自定义{@link com.cddx.common.security.wrapper.RequestWrapper}
 *
 * @author 范劲松
 */
@Log4j2
@Component
@WebFilter(filterName = "channelFilter", urlPatterns = {"/*"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ChannelFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            ServletRequest requestWrapper = null;
            if (request instanceof HttpServletRequest) {
                requestWrapper = new RequestWrapper((HttpServletRequest) request);
            }
            if (requestWrapper == null) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
