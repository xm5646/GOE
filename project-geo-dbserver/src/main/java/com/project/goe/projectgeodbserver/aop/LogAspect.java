package com.project.goe.projectgeodbserver.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
public class LogAspect {
    private static final Logger sLogger = Logger.getLogger(LogAspect.class);
    /**
     * 拦截com.toly1994.toly_mybatis.controller包下所以方法
     */
    @Pointcut("execution(public * com.project.goe.projectgeodbserver.controller.*.*(..))")
    public void log() {

    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("log()")//log()方法之前
    public void doBefore(JoinPoint joinPoint) {
        //接收请求，记录请求
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        //记录日志
        sLogger.info("url" + request.getRequestURI().toString());
        sLogger.info("method" + request.getMethod());
        sLogger.info("ip" + request.getRemoteAddr());

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            sLogger.info("name:" + name + ",value:{" + request.getParameter(name) + "}");

        }
    }

    /**
     * 后置通知
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = " log() ")
    public void doAfter(Object ret) {
        sLogger.info("response:" + ret);//处理完成，返回
    }
}