package com.crscd.framework.util.text;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @author lzy
 * @since 1.0
 */
public class StringUtil {
    /**
     * 字符串分隔符
     */
    public static final String SEPARATOR = String.valueOf((char) 29);
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("_[a-z]");

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 判断字符串是否为空,null和""都认为是空的
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 若字符串为空，则取默认值
     */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return StringUtils.defaultIfEmpty(str, defaultValue);
    }

    /**
     * 将string转成integer
     */
    public static Integer toInteger(String str) {
        if (isNumber(str)) {
            return Integer.valueOf(str);
        }
        return null;
    }

    /**
     * 将string转换成int
     */
    public static int toInt(String str) throws Exception {
        if (isNumber(str)) {
            return Integer.parseInt(str);
        }
        throw new Exception("the string is not number! " + str);
    }

    /**
     * 替换固定格式的字符串（支持正则表达式）
     */
    public static String replaceAll(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 是否为数字（整数或小数）
     */
    public static boolean isNumber(String str) {
        return NumberUtils.isCreatable(str);
    }

    /**
     * 是否为十进制数（整数）
     */
    public static boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelhumpToUnderline(String str) {
        Matcher matcher = UPPER_CASE_PATTERN.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
        }
        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = LOWER_CASE_PATTERN.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    /**
     * 分割固定格式的字符串
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }

    /**
     * 将字符串首字母大写
     */
    public static String firstToUpper(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 将字符串首字母小写
     */
    public static String firstToLower(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 转为帕斯卡命名方式（如：FooBar）
     */
    public static String toPascalStyle(String str, String seperator) {
        return StringUtil.firstToUpper(toCamelhumpStyle(str, seperator));
    }

    /**
     * 转为驼峰命令方式（如：fooBar）
     */
    public static String toCamelhumpStyle(String str, String seperator) {
        return StringUtil.underlineToCamelhump(toUnderlineStyle(str, seperator));
    }

    /**
     * 转为下划线命名方式（如：foo_bar）
     */
    public static String toUnderlineStyle(String str, String seperator) {
        str = str.trim().toLowerCase();
        if (str.contains(seperator)) {
            str = str.replace(seperator, "_");
        }
        return str;
    }

    /**
     * 转为显示命名方式（如：Foo Bar）
     */
    public static String toDisplayStyle(String str, String seperator) {
        String displayName = "";
        str = str.trim().toLowerCase();
        if (str.contains(seperator)) {
            String[] words = StringUtil.splitString(str, seperator);
            for (String word : words) {
                displayName += StringUtil.firstToUpper(word) + " ";
            }
            displayName = displayName.trim();
        } else {
            displayName = StringUtil.firstToUpper(str);
        }
        return displayName;
    }

    /**
     * 在字符串中删除指定的字符串（如，"abcded" 删除 abc后剩下ded）
     */
    public static String deletePart(String str, String substr) {
        return str.replace(substr, "");
    }

    /**
     * @param str
     * @return
     * @see "删除字符串中的字母"
     */
    public static String deleteAlphabet(String str) {
        return str.replaceAll("[a-zA-Z]", "");
    }

    /**
     * @param str
     * @return
     * @see "删除字符串中的数字"
     */
    public static String deleteNumber(String str) {
        return str.replaceAll("[0-9]", "");
    }

    /**
     * 删除反斜杠
     *
     * @param str
     * @return
     */
    public static String deleteSlash(String str) {
        return str.replaceAll("\\\\", "");
    }

    /**
     * 返回正则表达式匹配的字符串
     */
    public static List<String> getMatchStringList(String str, String regex) {
        List<String> matchStringList = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            matchStringList.add(m.group());
        }
        return matchStringList;
    }

    /**
     * 返回正则表达式匹配的第一个字符串
     */
    public static String getMatchString(String str, String regex) {
        List<String> matchStringList = getMatchStringList(str, regex);
        if (matchStringList != null && !matchStringList.isEmpty()) {
            return matchStringList.get(0);
        }
        return null;
    }


    /**
     * 删除json中内层大括号两侧的双引号
     *
     * @param str
     */
    public static String deleteInnerQuotationOfJson(String str) {
        String[] strs1 = str.split("\"\\{");
        String newStr1 = strs1[0];
        for (int i = 1; i < strs1.length; i++) {
            newStr1 = newStr1 + "{" + strs1[i];
        }
        String[] strs2 = newStr1.split("\\}\"");
        String newStr2 = strs2[0];
        for (int i = 1; i < strs2.length; i++) {
            newStr2 = newStr2 + "}" + strs2[i];
        }
        return newStr2;
    }

    /**
     * 根据符号分割字符串
     *
     * @param str
     * @param split
     * @return
     */
    public static List<String> stringToList(String str, String split) {
        List<String> list = new ArrayList<>();
        if (isEmpty(str) || isEmpty(split)) {
            return list;
        }

        String[] array = str.split(split);
        Collections.addAll(list, array);
        return list;
    }
}
