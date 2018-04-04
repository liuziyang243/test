package com.crscd.passengerservice.broadcast.content.business;

import com.crscd.framework.translation.serviceinterface.TranslatorInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/16
 */
public class AbstractWildcardReplaceManager {
    private TranslatorInterface translator;

    public void setTranslator(TranslatorInterface translator) {
        this.translator = translator;
    }

    String getReplacedWord(Object property, String lan) {
        String word = property.toString();
        word = word.replaceAll("\\[", "").replaceAll("]", "");
        String[] wordList = word.split(",");
        List<String> strList = new ArrayList<>();
        // 对于list进行单独的处理
        if (wordList.length > 1) {
            for (String str : wordList) {
                strList.add(translator.makeTranslation(str, lan));
            }
            String preprocessedWord = strList.toString();
            return preprocessedWord.replaceAll("\\[", "").replaceAll("]", "");
        }
        return translator.makeTranslation(word, lan);
    }
}
