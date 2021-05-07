/**
 * 文件名：BarCodeUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：条形码生成工具类
 */
package com.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringPattenUtil {

    /**
     * 功能描述: 去掉中括号,花括号、英文括号，中文括号及相关括号里面的内容,然后再去掉特殊字符
     *
     * @param: context 内容
     * @return: java.lang.String
     * @date: 2019/8/26 0012 11:18
     */
    public static String ClearBracket(String context) {
        if (context.contains("-") || context.contains("_")) {
            if (context.contains("-")) {
                context = context.split("-")[0];
            } else {
                context = context.split("_")[0];
            }
        }
        // 修改原来的逻辑，防止右括号出现在左括号前面的位置
        String regStr = "[，。、|~！@#￥；【》‘“”、】《\\-？：，：{}%……&*（）\\=—+ A-Za-z0-9]";//去掉英文字母及数字
        String reg = "\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）|\\【.*?\\】";//去掉括号
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";//去掉特殊字符
        String blankSpe = "";

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(context);
        String str = matcher.replaceAll(blankSpe.trim());
        Pattern compile = Pattern.compile(regEx);
        Matcher matcher1 = compile.matcher(str);
        String newStr = matcher1.replaceAll(blankSpe.trim());
        return newStr.replaceAll(regStr, "").trim();
    }
    /**
     * 是否包含中文
     *
     * @param str 传入的字符串
     * @return boolean 返回类型，如果包含中文返回true,否则返回false
     */
    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 正则表达式来判断字符串中是否包含 单行注释
     *  此方法匹配是否包含注释 而不是整合都是注释行
     *
     * @param str 待检验的字符串
     * @return 返回是否包含
     */
    public static Boolean isSingleLineComment(String str) {
        String regex = "(.*)//.*?$";
        Matcher m = Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    /**
     * 正则表达式来判断字符串中是否包含 单行注释
     *
     * @param str 待检验的字符串
     * @return 返回是否包含
     */
    public static Boolean isMultiLineComment(String str) {
        String regex = "\\s+\\/\\*(\\s|.)*?\\*\\/";
        Matcher m = Pattern.compile(regex).matcher(str);
        return m.matches();
    }

}
