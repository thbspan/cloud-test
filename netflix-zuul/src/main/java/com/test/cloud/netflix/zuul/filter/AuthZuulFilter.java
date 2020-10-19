package com.test.cloud.netflix.zuul.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AuthZuulFilter extends ZuulFilter {
    /**
     * header token认证令牌默认的属性名称
     */
    private static final String DEFAULT_TOKEN_HEADER_NAME = "token";
    /**
     * 响应头部 userId 属性名称
     */
    private static final String DEFAULT_HEADER_NAME = "user-id";

    /**
     * token 和 userId 的映射 <2.3>
     */
    private static final Map<String, Integer> TOKENS = new HashMap<>();

    static {
        TOKENS.put("crjldCrAQdi0Bl9gKqO3/g==", 1);
    }
    @Override
    public String filterType() {
        // 前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader(DEFAULT_TOKEN_HEADER_NAME);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        // 进行认证
        Integer userId = TOKENS.get(token);

        if (userId == null) {
            // 认证不通过
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401); // 响应 401 状态码
            return null;
        }
        // 认证通过，添加userId 到 request Header中，方便后续请求从请求头部获取数据
        ctx.getZuulRequestHeaders().put(DEFAULT_HEADER_NAME, String.valueOf(userId));
        return null;
    }
}
