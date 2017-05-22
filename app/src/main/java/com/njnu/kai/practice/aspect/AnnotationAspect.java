//package com.njnu.kai.practice.aspect;
//
//import com.njnu.kai.support.LogUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//
///**
// * @author hongkai.qian
// * @version 1.0.0
// * @since 16-6-1
// */
//@Aspect
//public class AnnotationAspect {
//
//    private static final String TAG = "AnnotationAspect";
//
//    @Before("execution(@com.njnu.kai.support.TestFunction * *(..))")
//    public void printLog(JoinPoint joinPoint) {
//        LogUtils.e(TAG, "Entering TestFunction " + joinPoint.getSourceLocation());
//    }
//
//}
