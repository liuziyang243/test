package com.crscd.framework.translation.dao.daointerface;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/5
 * Time: 14:48
 */
public interface LanguageDAOInterface {
    /**
     * 判断是否存在某种语言种类
     */
    boolean exitLanKind(String lan);

    /**
     * 向数据库增加语言种类
     */
    boolean insertLan(String lan);

    /**
     * 获取数据库语言种类
     */
    List<String> getLanList();

    /**
     * 删除语言种类
     */
    boolean delLan(String lan);

    /**
     * 修改语言种类的名称
     */
    boolean modifyLan(String lan);
}
