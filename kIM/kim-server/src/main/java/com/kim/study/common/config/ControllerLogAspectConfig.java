package com.kim.study.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : xuxw.
 * Create Date  : 2022-03-23
 * Create Time  : 15:19
 * Description  : controller层日志切面
 * Project Name : project-business-sso
 * Package Name : com.meritdata.cloud.multianalysis.config
 */

@Slf4j
//切面类
@Aspect
@Component
public class ControllerLogAspectConfig {

    /**
     * 定义切点
     */
    @Pointcut("execution(* com.kim.study.*.controller..*(..))")
    public void requestServer() {
        log.info("ControllerLogAspectConfig ==> Aspect in ...");
    }

    @Around("requestServer()")
    public Object doAround(ProceedingJoinPoint point) {
        // 记录请求开始执行时间：
        long beginTime = System.currentTimeMillis();
        //获取请求信息
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) return null;
        HttpServletRequest request = sra.getRequest();

        // 获取代理地址、请求地址、请求类名、方法名
        String remoteAddress = this.getRemoteIP(request);
        String requestURI = request.getRequestURI();
        String methodName = point.getSignature().getName();
        String clazzName = point.getTarget().getClass().getSimpleName();

        // 获取请求参数：
        MethodSignature ms = (MethodSignature) point.getSignature();
        //获取请求参数类型
        String[] parameterNames = ms.getParameterNames();
        // 获取请求参数值
        Object[] parameterValues = point.getArgs();

        StringBuilder sb = this.getParamsStringBuffer(parameterNames, parameterValues);
        Object result = null;
        try {
            result = point.proceed();
            //this.recordLog(requestURI, remoteAddress, "成功");
        } catch (Throwable throwable) {
            // 请求操作失败
            // 记录错误日志
            log.error("Aspect error. IP: {}, URI: {}, controller: {}, method: {}, params list: {}.", remoteAddress, requestURI, clazzName, methodName, sb.toString(), throwable);
            //this.recordLog(requestURI, remoteAddress, "失败");
        }

        // 记录请求完成执行时间：
        long endTime = System.currentTimeMillis();
        long usedTime = endTime - beginTime;
        // 记录日志
        if (usedTime > 50) {
            log.info("Aspect success. use time: {}, IP: {}, URI: {}, controller: {}, method: {}, params list: {}.", usedTime, remoteAddress, requestURI, clazzName, methodName, sb.toString());
        }
        return result;
    }

    public String getRemoteIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * @param parameterNames
     * @param parameterValues
     * @return
     */
    private StringBuilder getParamsStringBuffer(String[] parameterNames, Object[] parameterValues) {
        StringBuilder sb = new StringBuilder();
        //组合请求参数，进行日志打印
        if (parameterNames != null && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                //去除MultipartFile 的拦截
                if (parameterNames[i].equals("bindingResult") || parameterValues[i] instanceof MultipartFile) {
                    break;
                }
                if ((parameterValues[i] instanceof HttpServletRequest) || (parameterValues[i] instanceof HttpServletResponse)) {
                    sb.append("[");
                    sb.append(parameterNames[i]).append("=").append(parameterValues[i]);
                    sb.append("]");
                } else {
                    sb.append("[");
                    sb.append(parameterNames[i]).append("=");
                    sb.append(JSON.toJSONString(parameterValues[i], SerializerFeature.WriteDateUseDateFormat));
                    sb.append("]");
                }
            }
        }

        return sb;
    }

}
