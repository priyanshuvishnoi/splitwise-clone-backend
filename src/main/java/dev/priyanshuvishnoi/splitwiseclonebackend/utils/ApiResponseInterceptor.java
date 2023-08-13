package dev.priyanshuvishnoi.splitwiseclonebackend.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ApiResponseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (response.getStatus() == HttpStatus.OK.value() && modelAndView != null && modelAndView.getModelMap() != null) {
            ApiResponse apiResponse = new ApiResponse(true, modelAndView.getModelMap(), new Msg(response.getStatus(), "OK"));
            modelAndView.clear();
            modelAndView.addObject(apiResponse);
        }
    }
}
