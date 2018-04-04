package com.crscd.framework.translation.dao.daointerface;

import com.crscd.framework.translation.po.EntryBean;

import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 14:48
 */
public interface EntryDaoInterface {
    /**
     * 获取指定语言种类的字典信息
     */
    Map<String, String> getEntryMap(String language);

    /**
     * 获取字典信息
     */
    List<EntryBean> getDictionary(String language);

    /**
     * 向数据库增加词条
     */
    Map<String, Boolean> insertEntryList(List<EntryBean> entryBeanList);

    /**
     * 删除词条
     */
    Map<String, Boolean> delEntryList(List<EntryBean> entryBeanList);

    /**
     * 修改词条的翻译
     */
    Map<String, Boolean> modifyEntryList(List<EntryBean> entryBeanList);

    /**
     * 判断是否存在某一个词条的翻译项
     */
    boolean exitEntry(String word, String lan);
}
