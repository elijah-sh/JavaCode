package com.fssc;

import com.common.BaseDto;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Generated;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class GetDTO {
    public static void main(String[] args) throws NoSuchFieldException {
        Claim claim = new Claim();
        // 新建Person对象，
        //List<Person> peoples = new ArrayList<>();
        //Person p1 = new Person("eee", 100);
        //Person p2 = new Person("e2e", 170);
        //Person p3 = new Person("aaa", 200);
        //peoples.add(p1);
        //peoples.add(p2);
        //peoples.add(p3);
        //claim.setId((long) 10001);
        //claim.setTable(new Date());
        //claim.setPeoples(peoples);
        //claim.setName("payApply");
        //Cat cat = new Cat("lala");
        //claim.setCat(cat);
        generate(claim, new BaseDto());

    }
    public static void generate(Object object, BaseDto dto) throws NoSuchFieldException {
        Object[] fieldNames = getFiledName(object);

        for (int j = 0; j < fieldNames.length; j++) { // 遍历所有属性
            String name = (String) fieldNames[j]; // 获取属性的名字
            try {
                Object value = getFieldValueByName(name, object);
                //System.out.println("key=" + name);
                System.out.println(name + " ： " + value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


    public static Map<Object, Object> getFiledNameAndValue(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] fieldNamStrings = new String[fields.length];
        Map<Object, Object> map = new HashMap<>();
        int i = 0;
        for (Field field : fields) {
            fieldNamStrings[i++] = field.getName();
            // 判断类型  是 集合还是什么 继承了 baseDto？
            Object val = null;
            try {
                val = getValue(field, object);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(field.getName(), val);
        }
        return map;
    }
    public static Object getValue(Field field, Object object) throws IllegalAccessException {
        // 第一个字母大写
        Map<Object, Object> map = new HashMap<>();

        String firstletter = field.getName().substring(0, 1).toUpperCase();
        String getter = "get" + firstletter + field.getName().substring(1);
        Method method;
        Object value;
        try {
            method = object.getClass().getMethod(getter, new Class[]{});
            value = method.invoke(object, new Object[]{});
            // 集合 继续遍历
            if (value instanceof Collection) {
                System.out.println("Collection:" + value);
                //
                getFiledNameAndValue(field);

            } else if (value instanceof BaseDto) {
                System.out.println("BaseDto:" + value);
            } else {
                map.put(field.getName(), value);
            }

            return value;
        } catch (NoSuchMethodException | InvocationTargetException e) {
        }
        return null;
    }


    public static String[] getFiledName(Object object) throws NoSuchFieldException {
        // 模版名字
        Class cl1 = object.getClass();
        Field[] fields = cl1.getDeclaredFields();
        String[] fieldNamStrings = new String[fields.length];
        int i = 0;
        for (Field field : fields) {

            // 判断类型  通过类型转成 class

            System.out.println(field.getType());
            Object cl1z = field.getType();
            Type type = field.getGenericType();

            //field.signature;
            //field.ge
            Field dto = cl1.getDeclaredField(field.getName());

            // 再次反射

            Class<?> fieldCls = type.getClass();
            Annotation ann = fieldCls.getAnnotation(Length.class);

            if (ann != null) {
                System.out.println(ann + " " + field.getName());
            }

            if ("java.util.List".equals(field.getType().getTypeName())) {
                Class clazz = (Class)((ParameterizedType) type).getActualTypeArguments()[0];
                //获取类型的类型参数类型。  你可以去查看jdk帮助文档对ParameterizedType的解释。
                // getFiledName(cl1z);
                Annotation an = clazz.getAnnotation(Length.class);
                if (an != null) {
                    System.out.println(an + " " + field.getName());
                }
            }



            if (cl1z instanceof Collection) {

                getFiledName(cl1z);
            }

            fieldNamStrings[i++] = field.getName();
            Generated generated = field.getType().getAnnotation(Generated.class);
            Length length = field.getType().getAnnotation(Length.class);
            if (generated != null) {
                System.out.println(generated + " " + field.getName() + " " + generated.value());
            }
            if (length != null) {
                System.out.println(length + " " + field.getName());
            }

        }
        return fieldNamStrings;

    }

    // 通过名字来获取值
    public static Object getFieldValueByName(String name, Object claim) throws IllegalAccessException {


        String firstletter = name.substring(0, 1).toUpperCase();

        String getter = "get" + firstletter + name.substring(1);

        Method method;

        Object value;
        try {

            Class cl1 = claim.getClass();

            method = cl1.getMethod(getter, new Class[]{});

            value = method.invoke(claim, new Object[]{});


            Length length = (Length)cl1.getAnnotation(Length.class);

            Generated generated = (Generated)cl1.getAnnotation(Generated.class);

            if (generated != null) {
                System.out.println(generated + " " + generated.toString() + " " + generated.value());

            }
            if (length != null) {
                System.out.println(length + " " + length.max() );

            }


            return value;
        } catch (NoSuchMethodException | InvocationTargetException e) {
        }
        return null;
    }


}
