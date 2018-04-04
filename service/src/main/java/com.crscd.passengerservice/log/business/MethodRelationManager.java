package com.crscd.passengerservice.log.business;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.DataSet;
import com.crscd.framework.translation.annotation.MakeTranslation;
import com.crscd.passengerservice.log.po.MethodRelationBean;

import java.util.*;

/**
 * Created by zs
 * Date:2016/5/31
 * Time:9:47
 */
public class MethodRelationManager {

    private List<MethodRelationBean> methodRelationBeanList;

    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public MethodRelationManager() {
    }

    private void init() {
        //从数据库中获取MethodRelationBean
        methodRelationBeanList = dataSet.selectList(MethodRelationBean.class);
    }


    public String findServiceType(String methodName) {
        String result = "other";
        for (MethodRelationBean s : methodRelationBeanList) {
            if (methodName.equals(s.getMethodName())) {
                result = s.getServiceType();
                break;
            }
        }
        return result;
    }


    public Map<String, String> getServiceType() {
        Map<String, String> serviceTypeResult = new LinkedHashMap<>();
        String serviceTypeAll = FrameworkConstant.QUERY_ALL;
        String translationAll = getTranslationResult(serviceTypeAll);
        serviceTypeResult.put(serviceTypeAll, translationAll);
        Map<String, String> serviceTypes = new TreeMap<>();
        for (MethodRelationBean s : methodRelationBeanList) {
            String serviceType = s.getServiceType();
            String translation = getTranslationResult(serviceType);
            serviceTypes.put(serviceType, translation);
        }
        Set<String> keySet = serviceTypes.keySet();
        for (String key : keySet) {
            serviceTypeResult.put(key, serviceTypes.get(key));
        }
        return serviceTypeResult;
    }


    @MakeTranslation
    private String getTranslationResult(String origin) {
        return origin;
    }
}
