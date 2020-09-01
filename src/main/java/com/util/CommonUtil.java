package com.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static final String ENCODING_UFT_8 = "UTF-8";


    private CommonUtil() {
        throw new AssertionError();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj)
            throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);

        ByteArrayInputStream bin =
                new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();

        // 说明：调用ByteArrayInputStream
        //或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，
        //这一点不同于对外部资源（如文件流）的释放
    }

    /**
     * 驼峰转大写下划线
     * @param str 驼峰风格字符串 "toStr"
     * @return 下划线风格字符串 "TO_STR"
     */
    public static String humpToUpperLine(String str) {
        // 匹配大写A-Z
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);

        return sb.toString().toUpperCase();
    }

    /**
     * 下划线转驼峰
     * @param str 驼峰风格字符串 "cmfClmClaimNpcexpLineList"
     * @return 下划线风格字符串 "CMF_CLM_CLAIM_NPCEXP_LINES"
     */
    public static String regionToTable(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString().replace("line_list", "lines");
    }

    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0
                    ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().replace("Lines", "LineList");
    }

    public static String stringByU8Byte(String str, int lengthb) {
        try {
            if (str == null || str.length() <= 0) {
                return null;
            }
            byte[] buf = str.getBytes(ENCODING_UFT_8);
            if (lengthb >= buf.length) {
                return str;
            }
            int left = Math.max(lengthb / 3, 1);
            int right = Math.min(str.length(), lengthb);
            int length = 0;
            int middle = 0;
            int middleLen = 0;
            while (middleLen != lengthb) {
                middle = (left + right) / 2;
                middleLen = str.substring(0, middle).getBytes(ENCODING_UFT_8).length;
                if (middleLen == lengthb) {
                    length = middle;
                    break;
                } else if (middleLen > lengthb) {
                    right = middle;
                } else if (middleLen < lengthb) {
                    left = middle;
                }
                if (left == right || (left + right) / 2 == left) {
                    if (left == 1 && str.substring(0, 1).getBytes(ENCODING_UFT_8).length > lengthb) {
                        left = 0;
                    }
                    length = left;
                    break;
                }
            }
            return str.substring(0, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "【支付失败派生的付款单】-LFH-一键全选前置单据-摘要过长派生测试摘要过长派生测试摘要过长派生测试摘要过长派生测试摘要过长派生测试摘要过长派生测试摘要过长派生测试摘要";
        System.out.println(str.getBytes().length);
        System.out.println(stringByU8Byte(str, 240));
        System.out.println(stringByU8Byte(str, 240).getBytes().length);

        Long ten = new Long(10);
        System.out.println(("10".equals(new Long(10))));
        System.out.println(("10".equals(String.valueOf(ten))));
        System.out.println( ten == 10);
        System.out.println( ten == null);
    }
}
