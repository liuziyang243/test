package com.crscd.passengerservice.aop;

import com.crscd.framework.translation.serviceinterface.TranslatorInterface;
import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.text.StringUtil;
import com.crscd.passengerservice.app.ServiceConstant;
import com.crscd.passengerservice.context.ContextHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.WebServiceContext;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author lzy
 * @create 2018-03-12 下午3:16
 */
@Aspect
//@Order(1)
public class TrackSOAPInterfaceAndTranslate extends AbstractTrackInterface {
    private static final Logger logger = LoggerFactory.getLogger(TrackSOAPInterfaceAndTranslate.class);

    public TrackSOAPInterfaceAndTranslate() {
    }

    /**
     * 对所有子包中的类的所有public方法有效
     */
    @Pointcut("execution(public * com.crscd.passengerservice.soapinterface.*.*(..)) && @annotation(com.crscd.framework.translation.annotation.MakeTranslation)")
    public void callTransInterface() {
    }

    @Around(value = "callTransInterface()")
    public Object around(ProceedingJoinPoint jp) {
        Object result = null;
        try {
            result = jp.proceed();
            logger.info("Make translate for {}", getMethodName(jp));
            result = makeTranslate(jp, result);
        } catch (Throwable throwable) {
            logger.error("[Translate aop error]:{}", throwable);
        }
        return result;
    }

    /**
     * 翻译结果
     *
     * @param jp
     * @param result
     * @return
     */
    private Object makeTranslate(ProceedingJoinPoint jp, Object result) {
        WebServiceContext wsctx = getWebServiceContext(jp);
        Map<String, String> headers = SoapHeaderUtil.getSoapHeaderList(wsctx);
        if (MapUtil.isEmpty(headers)) {
            return result;
        }
        //从soap header中直接取出客户端的语言
        String lan = headers.get(ServiceConstant.USER_LAN);
        if (StringUtil.isEmpty(lan)) {
            return result;
        }

        TranslatorInterface translator = ContextHelper.getTranslator();

        // 判断返回值是否是Map,如果是，则直接返回
        // 暂时不支持Map类型的数据翻译
        if (result instanceof Map) {
            return result;
        }
        // 判断返回值是否是列表
        else if (result instanceof List) {
            if (ListUtil.isNotEmpty((List) result)) {
                String type = ((List) result).get(0).getClass().getTypeName();
                return translator.makeTranslation((List) result, lan, type);
            } else {
                return result;
            }
        }
        // 如果都不是，则认为是对象
        else {
            return translator.makeTranslation(result, lan, getReturnType(jp).getTypeName());
        }
    }
}
