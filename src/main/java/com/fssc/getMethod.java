package com.fssc;

import com.common.BaseDto;
import com.common.Cat;
import com.common.Person;
import com.util.DtoRefUtil;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import static com.util.DtoRefUtil.fmtDate;
import static com.util.DtoRefUtil.parGetName;

/**
 * 获取类方法
 */
public class getMethod {



    public static void main(String[] args) {
        ClaimTmpl tmpl = new ClaimTmpl();
        Claim claim = new Claim();
        // 新建Person对象，

        //getFiled(tmpl, list, ss);
        getFiledValue("com.fssc.ClaimTmpl");
        Map<String, String> map = DtoRefUtil.getFieldValueMap(tmpl);

        System.out.println(map.toString());
    }

    public static List<String> getFiledValue(String classPath) {
        Class cls  = null;
        try {
            cls  = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<String> data = new ArrayList<>();

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());

            data.add(method.getName());
        }

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            data.add(field.getName());
        }

        return data;

    }



    public static void getFiled(Object object, List<Snapshot> list, Snapshot ss) {

        Field[] fields = object.getClass().getDeclaredFields();
        Map<Object, Object> map = new HashMap<>();
        for (Field field : fields) {

            // 查看列的注释 只遍历数据库字段
            Annotation[] annotations = field.getDeclaredAnnotations();


            // 判断类型  是 集合还是什么 继承了 baseDto？
            Object value = getFieldValue(field, object);

            // 集合 继续遍历
            if (value instanceof Collection) {
                System.out.println("Collection:" + value);
                ss.setRegionCode(field.getName());
                getFiledList((List)value, list, ss);

            } else if (value instanceof BaseDto) {
                System.out.println("BaseDto:" + value);
                ss.setRegionCode(field.getName());
                getFiled((Object)value, list, ss);
            } else {
                map.put(field.getName(), value);

                Snapshot snapshot = new Snapshot();
                snapshot.setComponentCode(field.getName());
                snapshot.setComponentValue(String.valueOf(value));
                if (!StringUtils.isEmpty(ss.getRegionCode())) {
                    snapshot.setRegionCode(ss.getRegionCode());
                }
                if (ss.getRegionLineId() != null) {
                    snapshot.setRegionLineId(ss.getRegionLineId());
                }
                list.add(snapshot);
            }
        }
    }
    public static void getFiledList(List<Object> object, List<Snapshot> list, Snapshot ss) {

        Long num = Long.valueOf(1);
        for (Object o : object) {
            ss.setRegionLineId(num++);
            getFiled(o, list, ss);
        }
    }

        // 获取值
    public static Object getFieldValue(Field field, Object bean) {
        String fieldGetName = parGetName(field.getName());
        Class<?> cls = bean.getClass();

        try {

            Method fieldGetMet = cls.getMethod(fieldGetName, new Class[]{});
            Object value = fieldGetMet.invoke(bean, new Object[]{});
            String fieldType = field.getType().getSimpleName();

            //Method method = object.getClass().getDeclaredMethod(fieldGetName, Object.class);
            //Object value = method.invoke(object, new Object[]{});
            if ("Date".equals(fieldType)) {
                value = fmtDate((Date) value);
            }
            if ("BigDecimal".equals(fieldType)) {
                BigDecimal bd = (BigDecimal) value;
                bd.setScale(2, BigDecimal.ROUND_HALF_UP);

            }
            //else {
            //    if (null != fieldVal) {
            //        result = String.valueOf(fieldVal);
            //    }
            //}
            return value;
        } catch (NoSuchMethodException | InvocationTargetException e) {
            System.out.println("N" + e);

        } catch (IllegalAccessException e) {
            System.out.println("I" + e);
        }
        return null;
    }


}
