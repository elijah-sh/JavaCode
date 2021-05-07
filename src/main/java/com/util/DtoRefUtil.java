/**
 * 文件名：DtoRefUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：通过反射动态获取get、set方法，并且动态对其赋值
 */
package com.util;



import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 通过反射动态获取get、set方法，并且动态对其赋值
 */
public final class DtoRefUtil {

    //private static Logger logger = LoggerFactory.getLogger(DtoRefUtil.class);
    private static final char COLON = ':';
    private static final char UNDERLINE = '_';
    private static final int THREE = 3;

    private DtoRefUtil() {
    }

    /**
     * 取Bean的属性和值对应关系的MAP
     *
     * @param bean object实体对象
     * @return Map 结果集
     */
    public static Map<String, String> getFieldValueMap(Object bean) {
        Class<?> cls = bean.getClass();
        Map<String, String> valueMap = new HashMap<String, String>();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldType = field.getType().getSimpleName();
                String fieldGetName = parGetName(field.getName());
                if (!checkGetMet(methods, fieldGetName)) {
                    continue;
                }
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[]{});
                Object fieldVal = fieldGetMet.invoke(bean, new Object[]{});
                String result = null;
                if ("Date".equals(fieldType)) {
                    result = fmtDate((Date) fieldVal);
                } else {
                    if (null != fieldVal) {
                        result = String.valueOf(fieldVal);
                    }
                }
                valueMap.put(field.getName(), result);
            } catch (Exception e) {
                //logger.error(e.getMessage());
                continue;
            }
        }
        return valueMap;
    }

    /**
     * set属性的值到Bean
     *
     * @param bean   object实体对象
     * @param valMap 字符集合
     */
    public static void setFieldValue(Object bean, Map<String, String> valMap) {
        Class<?> cls = bean.getClass();
        // 取出bean里的所有方法
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                String fieldSetName = parSetName(field.getName());
                if (!checkSetMet(methods, fieldSetName)) {
                    continue;
                }
                Method fieldSetMet = cls.getMethod(fieldSetName,
                        field.getType());
                String fieldKeyName = field.getName();
                String value = valMap.get(fieldKeyName);
                if (null != value && !"".equals(value)) {
                    String fieldType = field.getType().getSimpleName();
                    if ("String".equals(fieldType)) {
                        fieldSetMet.invoke(bean, value);
                    } else if ("Date".equals(fieldType)) {
                        Date temp = parseDate(value);
                        fieldSetMet.invoke(bean, temp);
                    } else if ("Integer".equals(fieldType)
                            || "int".equals(fieldType)) {
                        Integer intval = Integer.parseInt(value);
                        fieldSetMet.invoke(bean, intval);
                    } else if ("Long".equalsIgnoreCase(fieldType)) {
                        Long temp = Long.parseLong(value);
                        fieldSetMet.invoke(bean, temp);
                    } else if ("Double".equalsIgnoreCase(fieldType)) {
                        Double temp = Double.parseDouble(value);
                        fieldSetMet.invoke(bean, temp);
                    } else if ("Boolean".equalsIgnoreCase(fieldType)) {
                        Boolean temp = Boolean.parseBoolean(value);
                        fieldSetMet.invoke(bean, temp);
                    } else {
                        //logger.debug("not supper type" + fieldType);
                    }
                }
            } catch (Exception e) {
                //logger.error(e.getMessage());
                continue;
            }
        }
    }

    /**
     * 格式化string为Date
     *
     * @param datestr 待转换字段
     * @return date 转换的日期结果
     */
    public static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            String fmtstr = null;
            if (datestr.indexOf(COLON) > 0) {
                fmtstr = "yyyy-MM-dd HH:mm:ss";
            } else {
                fmtstr = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);
            return sdf.parse(datestr);
        } catch (Exception e) {
            //logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 日期转化为String
     *
     * @param date 待转换日期字段
     * @return date 日期转换的字符串结果
     */
    public static String fmtDate(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.US);
            return sdf.format(date);
        } catch (Exception e) {
            //logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 判断是否存在某属性的 set方法
     *
     * @param methods     对应的方法数组
     * @param fieldSetMet set方法名称
     * @return boolean true -> 存在 false -> 不存在
     */
    public static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param methods     对应的方法数组
     * @param fieldGetMet get方法名称
     * @return boolean true -> 存在 false -> 不存在
     */
    public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接某属性的 get方法
     *
     * @param fieldName 字段名
     * @return String 结果
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == UNDERLINE) {
            startIndex = 1;
        }
        return "get"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName 字段名
     * @return String 结果
     */
    public static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == UNDERLINE) {
            startIndex = 1;
        }
        return "set"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 获取存储的键名称（调用parGetName）
     *
     * @param fieldName 字段名称
     * @return fieldGetName 去掉开头的get
     */
    public static String parKeyName(String fieldName) {
        String fieldGetName = parGetName(fieldName);
        //if (!StringUtils.isEmpty(fieldGetName) && fieldGetName.length() > THREE) {
        //    return fieldGetName.substring(THREE);
        //}
        return fieldGetName;
    }

}
