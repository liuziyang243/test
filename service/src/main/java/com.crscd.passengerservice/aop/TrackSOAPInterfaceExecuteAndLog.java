package com.crscd.passengerservice.aop;

import com.crscd.framework.util.collection.MapUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.log.business.OperationLogManager;
import com.crscd.passengerservice.result.base.ResultMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.WebServiceContext;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-03-02 下午1:13
 */
@Aspect
//@Order(3)
public class TrackSOAPInterfaceExecuteAndLog extends AbstractTrackInterface {
    private static final Logger logger = LoggerFactory.getLogger(TrackSOAPInterfaceExecuteAndLog.class);

    public TrackSOAPInterfaceExecuteAndLog() {
    }

    /**
     * 对所有子包中的类的所有public方法有效且需要带有@MakeRecord注解
     */
    @Pointcut("execution(public * com.crscd.passengerservice.soapinterface.*.*(..)) && @annotation(com.crscd.framework.annotation.MakeRecord)")
    public void callLogInterface() {
    }

    /**
     * 记录用户访问后台的接口情况作为用户的操作记录
     *
     * @param jp
     * @param rvt
     */
    @AfterReturning(returning = "rvt", pointcut = "callLogInterface()")
    public void after(JoinPoint jp, Object rvt) {
        WebServiceContext wsctx = getWebServiceContext(jp);
        Map<String, String> headers = SoapHeaderUtil.getSoapHeaderList(wsctx);
        if (MapUtil.isNotEmpty(headers)) {
            String methodResult = "success";
            if (null != rvt) {
                if (rvt.getClass() == Boolean.class && !((Boolean) rvt)) {
                    methodResult = "fail";
                } else if (rvt.getClass() == ResultMessage.class && !((ResultMessage) rvt).getResult()) {
                    methodResult = "fail";
                }
            }
            //调用保存log的方法
            String methodName = getMethodName(jp);
            Map<String, Object> paramMap = getMethodArgs(jp);
            logger.info("Make log for {}", methodName);

            // 如果在本类中注入manager，则会出现调用方法的时候manager为空
            // 只能改成从方法中从ioc容器中获取manager
            // 经过单步调试，确认在初始化容器的时候已经将manager注入
            // 初步分析，因为默认情况下aspectj实现的是编译时织入，即在编译的时候将切面代码织入到目标位置
            // 此时即代码已经写进了切点中，然后spring在运行时进行初始化是没办法将manager注入到切点代码中的。
            // 只能从容器中获取manager
            OperationLogManager manager = ContextHelper.getOperationLogManager();

            manager.logRecord(headers.get(ServiceConstant.USER_STATION), headers.get(ServiceConstant.USER_NAME), methodName, paramMap, methodResult);
        }
    }
}
