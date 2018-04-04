package com.crscd.framework.translation.po;

import com.crscd.framework.orm.annotation.OrmIgnore;

/**
 * Created by lzy
 * Date: 2016/6/3
 * Time: 15:01
 */
public class EntryBean {
    /**
     * id
     **/
    @OrmIgnore
    private long id;
    /**
     * 英文主键
     **/
    private String originalWord;
    /**
     * 翻译的对象语言
     **/
    private String translationWord;
    /**
     * 翻译对象的语言种类
     **/
    private String languageName;
    /**
     * 修改位标记:
     * - 0表示禁止修改和删除，
     * - 1表示禁止删除，
     * - 2表示可以修改和删除
     */
    private int reviseFlag;

    public EntryBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getReviseFlag() {
        return reviseFlag;
    }

    public void setReviseFlag(int reviseFlag) {
        this.reviseFlag = reviseFlag;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getTranslationWord() {
        return translationWord;
    }

    public void setTranslationWord(String translationWord) {
        this.translationWord = translationWord;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
