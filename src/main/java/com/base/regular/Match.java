package com.base.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {

    public static void main(String args[]) {

        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String fieldName = "<#if (hashMap.cmfClmClaimNpcexpLineList.caTaskNum.displayFlag)!false ||(RequestParameters.isReptile)??> ";
        // 字符/数字/。
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        //if (m.find()) {
        //    System.out.println("Found value: " + m.group(0));
        //    System.out.println("Found value: " + m.group(1));
        //    System.out.println("Found value: " + m.group(2));
        //    System.out.println("Found value: " + m.group(3));
        //} else {
        //    System.out.println("NO MATCH");
        //}


        // 要匹配多内容
        String commentsStr = "is single line//this i" +
                "//s single line comments";
        // 匹配规则  单行注释
        // ^表示字符串的开始  .表示任意字符  *0或多个前面出现的正则表达式  $表示字符串行末或换行符之前
        Pattern singleLineCommentP = Pattern.compile("^//.*?$");
        Matcher foundSingleLineComment = singleLineCommentP.matcher(commentsStr);

        // 完全注释
        if (foundSingleLineComment.matches()) {
            System.out.println(commentsStr);
        }

    }
}
