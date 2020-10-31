package cn.zkz.animal.filter;

import cn.zkz.animal.exception.AniamlException;
import cn.zkz.animal.util.LoginUtil;
import cn.zkz.animal.util.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Order(1)
@WebFilter(filterName = "OpenIdInterceptor")
@Service
public class OpenIdInterceptor implements Filter {

    private Logger log = LogManager.getLogger(OpenIdInterceptor.class);

    @Autowired
    private LoginUtil loginUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.indexOf("/openId/") == -1) {
            filterChain.doFilter(httpServletRequest, servletResponse);
            return;
        }
        ChangeRequestWrapper requestWrapper = new ChangeRequestWrapper(httpServletRequest);
        Map<String, String[]> params = new HashMap<>();
        String openId = loginUtil.getUserId(httpServletRequest);

        if (StringUtils.isBlank(openId)) {
            log.error("获取openID失败，openId={}", openId);

            throw new AniamlException(Result.INVALID_OPENID_CODE, Result.INVALID_OPENID_MSG);

        }

        params.put("openId", new String[]{openId});
        requestWrapper.setParameterMap(params);

        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
