package com.donkeycode.boot.annotation;

import org.springframework.aop.framework.AopProxyUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;


/**
 * @author yanjun.xue
 */
public class AnnotationUtils {

    /**
     * @param cls
     * @param annotationClass
     * @param <T>
     * @return
     */
    private static <T extends Annotation> T getAnnotation(Class<?> cls, Class<T> annotationClass) {

        if (cls == null || annotationClass == null) {
            return null;
        }
        T res = cls.getAnnotation(annotationClass);
        if (res == null) {
            for (Annotation annotation : cls.getAnnotations()) {
                if (annotation instanceof Documented) {
                    continue;
                }
                res = getAnnotation(annotation.annotationType(), annotationClass);
                if (res != null) {
                    break;
                }
            }
        }
        return res;
    }

    /**
     * @param obj
     * @param annotationClass
     * @param <Obj>
     * @param <T>
     * @return
     */
    public static <Obj, T extends Annotation> T getAnnotation(Obj obj, Class<T> annotationClass) {
        if (obj == null) {
            return null;
        }
        return getAnnotation(AopProxyUtils.ultimateTargetClass(obj), annotationClass);
    }

    /**
     * @param obj
     * @param <Obj>
     * @return
     */
    public static <Obj> String getResourceType(Obj obj) {
        if (obj == null) {
            return null;
        }
        ResourceInfo info = getAnnotation(obj, ResourceInfo.class);
        return info == null ? null : info.type();
    }

    /**
     * @param obj
     * @param <Obj>
     * @return
     */
    public static <Obj> String getFunctionName(Obj obj) {
        if (obj == null) {
            return null;
        }
        SelfDefinedSearch definedSearch = getAnnotation(obj, SelfDefinedSearch.class);
        return definedSearch == null ? null : definedSearch.functionName();
    }
}
