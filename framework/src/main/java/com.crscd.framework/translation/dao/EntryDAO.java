package com.crscd.framework.translation.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.translation.dao.daointerface.EntryDaoInterface;
import com.crscd.framework.translation.po.EntryBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 14:53
 */
public class EntryDAO implements EntryDaoInterface {
    private final String condition = "languageName=?";
    private DataSet dataSet;

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public List<EntryBean> getDictionary(String language) {
        return dataSet.selectListWithCondition(EntryBean.class, condition, language);
    }

    @Override
    public Map<String, String> getEntryMap(String language) {
        List<EntryBean> beans = getDictionary(language);
        Map<String, String> result = new HashMap<>();
        for (EntryBean bean : beans) {
            result.put(bean.getOriginalWord().toLowerCase(), bean.getTranslationWord());
        }
        return result;
    }

    @Override
    public Map<String, Boolean> insertEntryList(List<EntryBean> entryBeanList) {
        int[] result = dataSet.insertListWithResultList(entryBeanList);
        Map<String, Boolean> resultMap = new HashMap<>();
        int index = 0;
        for (EntryBean bean : entryBeanList) {
            if (result[index] > 0) {
                resultMap.put(bean.getOriginalWord(), true);
            } else {
                resultMap.put(bean.getOriginalWord(), false);
            }
            index += 1;
        }
        return resultMap;
    }

    @Override
    public Map<String, Boolean> delEntryList(List<EntryBean> entryBeanList) {
        Map<String, Boolean> resultMap = new HashMap<>();
        for (EntryBean bean : entryBeanList) {
            boolean flag = dataSet.delete(bean);
            resultMap.put(bean.getLanguageName() + ":" + bean.getOriginalWord(), flag);
        }
        return resultMap;
    }

    @Override
    public Map<String, Boolean> modifyEntryList(List<EntryBean> entryBeanList) {
        Map<String, Boolean> resultMap = new HashMap<>();
        for (EntryBean bean : entryBeanList) {
            boolean flag = dataSet.update(bean);
            resultMap.put(bean.getLanguageName() + ":" + bean.getOriginalWord(), flag);
        }
        return resultMap;
    }

    @Override
    public boolean exitEntry(String word, String lan) {
        String exitCondition = "originalWord=? AND languageName=?";
        return dataSet.selectCount(EntryBean.class, exitCondition, word, lan) > 0;
    }
}
