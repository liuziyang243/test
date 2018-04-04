package com.crscd.framework.translation.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.translation.dao.daointerface.LanguageDAOInterface;
import com.crscd.framework.translation.po.EntryBean;
import com.crscd.framework.translation.po.LanguageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 14:55
 */
public class LanguageDAO implements LanguageDAOInterface {
    private final String condition = "languageName=?";
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public boolean exitLanKind(String lan) {
        return dataSet.selectCount(LanguageBean.class, condition, lan) > 0;
    }

    @Override
    public boolean insertLan(String lan) {
        return dataSet.insert(new LanguageBean(lan));
    }

    @Override
    public List<String> getLanList() {
        List<String> lanList = new ArrayList<>();
        List<LanguageBean> beanList = dataSet.selectList(LanguageBean.class);
        for (LanguageBean bean : beanList) {
            lanList.add(bean.getLanguageName());
        }
        return lanList;
    }

    @Override
    public boolean delLan(String lan) {
        // 首先清理词条
        boolean flag = dataSet.delete(EntryBean.class, condition, lan);
        return flag && dataSet.delete(LanguageBean.class, condition, lan);
    }

    @Override
    public boolean modifyLan(String lan) {
        // 首先更新词条对应的语言名称
        boolean flag = dataSet.update(EntryBean.class, condition, lan);
        return flag && dataSet.update(LanguageBean.class, condition, lan);
    }
}
