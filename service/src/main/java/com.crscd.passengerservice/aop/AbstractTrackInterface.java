package com.crscd.passengerservice.aop;

import com.crscd.passengerservice.soapinterface.implement.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import javax.xml.ws.WebServiceContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-03-12 下午3:17
 */
public abstract class AbstractTrackInterface {

    /**
     * 从Joinpoit中获取WebServiceContext
     *
     * @param jp
     * @return
     */
    WebServiceContext getWebServiceContext(JoinPoint jp) {
        if (jp.getThis() instanceof PlanServiceInterfaceImpl) {
            PlanServiceInterfaceImpl impl = (PlanServiceInterfaceImpl) jp.getThis();
            return impl.getContext();
        } else if (jp.getThis() instanceof BroadcastServiceInterfaceImpl) {
            BroadcastServiceInterfaceImpl impl = (BroadcastServiceInterfaceImpl) jp.getThis();
            return impl.getContext();
        } else if (jp.getThis() instanceof GuideServiceInterfaceImpl) {
            GuideServiceInterfaceImpl impl = (GuideServiceInterfaceImpl) jp.getThis();
            return impl.getContext();
        } else if (jp.getThis() instanceof ExosystemServiceInterfaceImpl) {
            ExosystemServiceInterfaceImpl impl = (ExosystemServiceInterfaceImpl) jp.getThis();
            return impl.getContext();
        } else if (jp.getThis() instanceof LogServiceInterfaceImpl) {
            LogServiceInterfaceImpl impl = (LogServiceInterfaceImpl) jp.getThis();
            return impl.getContext();
        } else if (jp.getThis() instanceof SystemServiceInterfaceImpl) {
            SystemServiceInterfaceImpl impl = (SystemServiceInterfaceImpl) jp.getThis();
            return impl.getContext();
        } else {
            return null;
        }
    }

    /**
     * 判断连接点方法是否包含指定注解签名
     *
     * @param jp
     * @param target
     * @return
     */
    boolean containsAnnotation(JoinPoint jp, Class<? extends Annotation> target) {
        Method method = getMethod(jp);
        return method.isAnnotationPresent(target);
    }

    /**
     * 获取连接点函数名
     *
     * @param jp
     * @return
     */
    String getMethodName(JoinPoint jp) {
        Method method = getMethod(jp);
        return method.getName();
    }

    /**
     * 获取函数
     *
     * @param jp
     * @return
     */
    Method getMethod(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        //被拦截的方法
        return methodSignature.getMethod();
    }

    /**
     * 获取连接点的参数
     *
     * @param jp
     * @return
     */
    Map<String, Object> getMethodArgs(JoinPoint jp) {
        Map<String, Object> argsMap = new HashMap<>();
        //获取被截取方法的入参名及入参值
        Object[] paramValues = jp.getArgs();
        String[] paramNames = ((CodeSignature) jp.getSignature()).getParameterNames();

        //剔除password的截取
        for (int i = 0; i < paramNames.length; i++) {
            argsMap.put(paramNames[i], paramValues[i]);
        }
        return argsMap;
    }

    /**
     * 获取返回值类型
     *
     * @param jp
     * @return
     */
    public Class getReturnType(ProceedingJoinPoint jp) {
        Signature signature = jp.getSignature();
        return ((MethodSignature) signature).getReturnType();
    }

}
