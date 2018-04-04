package com.crscd.passengerservice.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-03-12 下午5:08
 */
@Aspect
public class TrackSOAPInterfaceError extends AbstractTrackInterface {
    private static final Logger logger = LoggerFactory.getLogger(TrackSOAPInterfaceError.class);

    public TrackSOAPInterfaceError() {
    }

    /**
     * 对所有子包中的类的所有public方法有效
     */
    @Pointcut("execution(public * com.crscd.passengerservice.soapinterface.*.*(..))")
    public void callInterface() {
    }

    @AfterThrowing(value = "callInterface()", throwing = "ex")
    public void processRuntimeError(Throwable ex) {
        logger.error("[Call soap interface error] {}", ex);
    }
}
