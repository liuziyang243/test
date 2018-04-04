package com.crscd.framework.dao.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

/**
 * @author lzy
 * <p>
 * Create Time: 2018/3/13 10:56
 * @version v1.00
 */
@Aspect
public class ProProcessParams {
    private final String PARAMS_NAME = "params";

    public ProProcessParams() {
    }

    /**
     * 对所有子包中的类的所有public方法有效
     */
    @Pointcut("execution(public * com.crscd.framework.orm.DataSet.*(..))")
    public void callDao() {
    }

    @Before("callDao()")
    public void before(JoinPoint jp) {
        //获取被截取方法的入参
        Object[] paramValues = jp.getArgs();
        String[] paramNames = ((CodeSignature) jp
                .getSignature()).getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            if (PARAMS_NAME.equals(paramNames[i])) {
                preProcessParams(paramValues[i]);
            }
        }
    }

    // 将enum类型的数据转为string类型
    private void preProcessParams(Object param) {
        Object[] params = (Object[]) param;
        // dbutils不支持enum直接传入作为参数，需要做一下转型
        if (null != params) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Enum) {
                    params[i] = params[i].toString();
                }
            }
        }
    }
}
