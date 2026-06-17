package com.ban.carbonaccount.interceptor;

import com.ban.carbonaccount.enums.ResultEnum;
import com.ban.carbonaccount.exception.BizException;
import com.ban.carbonaccount.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            throw new BizException(ResultEnum.TOKEN_ERROR);
        }

        Long userId = jwtUtil.getUserId(token);
        // 将用户ID放入请求域，后续接口直接获取
        request.setAttribute("userId", userId);
        return true;
    }
}