package com.fssc;

import com.common.BaseDto;
import com.common.Cat;
import com.common.Person;
import com.sun.tools.javac.util.ArrayUtils;
import com.util.DtoRefUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class test {
    public static void main(String[] args) {
        Claim claim = new Claim();
        // 新建Person对象，
        List<Person> peoples = new ArrayList<>();
        Person p1 = new Person("eee", 100);
        Person p2 = new Person("e2e", 170);
        Person p3 = new Person("aaa", 200);
        peoples.add(p1);
        peoples.add(p2);
        peoples.add(p3);
        claim.setId((long) 10001);
        claim.setTable(new Date());
        claim.setPeoples(peoples);
        claim.setName("payApply");
        Cat cat = new Cat("lala");
        claim.setCat(cat);
        generate(claim, new BaseDto());


        Map<String, String> map = DtoRefUtil.getFieldValueMap(claim);

        //System.out.println(map.toString());

    }
    public static void generate(Object object, BaseDto dto) {
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

        for (Object name : fieldNames) {
            try {
                Object value = getFieldValue((String) name, object);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 判断类型  是 集合还是什么 继承了 baseDto？
        }
    }

    public static Object getFieldValue(String name, Object object) throws IllegalAccessException {
        // 第一个字母大写
        String firstletter = (String)name.substring(0, 1).toUpperCase();
        String getter = "get" + firstletter + name.substring(1);
        Method method;
        Object value;
        try {
            method = object.getClass().getMethod(getter, new Class[]{});
            value = method.invoke(object, new Object[]{});
            // 集合 继续遍历
            if (value instanceof Collection) {
                System.out.println("Collection:" + value);
                //

            }
            if (value instanceof BaseDto) {
                System.out.println("BaseDto:" + value);
            }
            return value;
        } catch (NoSuchMethodException | InvocationTargetException e) {
        }
        return null;
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

    public static void getFiled(Object object) {

        Field[] fields = object.getClass().getDeclaredFields();
        String[] fieldNamStrings = new String[fields.length];
        Map<Object, Object> map = new HashMap<>();
        for (Field field : fields) {
            // 判断类型  是 集合还是什么 继承了 baseDto？
            Object value = getFieldValue(field, object);

            // 集合 继续遍历
            if (value instanceof Collection) {
                System.out.println("Collection:" + value);
                //
                getFiled(field);

            } else if (value instanceof BaseDto) {
                System.out.println("BaseDto:" + value);
                getFiled(field.getName());
            } else {
                map.put(field.getName(), value);
            }
        }
    }
    // 获取值
    public static Object getFieldValue(Field field, Object object) {

        String firstletter = field.getName().substring(0, 1).toUpperCase();
        String getter = "get" + firstletter + field.getName().substring(1);
        try {
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(object, new Object[]{});
            return value;
        } catch (NoSuchMethodException | InvocationTargetException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    //public static void generate(Object claim, BaseDto dto) {
    //    // 处理@Childern注解的属性token
    //    Object[] fieldNames = getFiledName(claim);
    //
    //    for (Object f : fieldNames) {
    //        try {
    //            Object fieldValue = getFiledName(dto, f.getName());
    //            if (fieldValue instanceof BaseDto) {
    //                generate(claim, (BaseDto) fieldValue);
    //            } else if (fieldValue instanceof Collection) {
    //                //generateAndSetToken(securityKey, (Collection) fieldValue);
    //            }
    //        } catch (Exception e) {
    //            throw new RuntimeException(e);
    //        }
    //    }
    //}

    public static String[] getFiledName(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] fieldNamStrings = new String[fields.length];

        int i = 0;
        for (Field field : fields) {
            fieldNamStrings[i++] = field.getName();
            // 判断类型  是 集合还是什么 继承了 baseDto？
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
            method = claim.getClass().getMethod(getter, new Class[]{});

            value = method.invoke(claim, new Object[]{});

            // 集合 继续遍历
            if (value instanceof Collection) {
                System.out.println("Collection:" + value);
                //

            }
            if (value instanceof BaseDto) {
                System.out.println("BaseDto:" + value);
            }

            return value;
        } catch (NoSuchMethodException | InvocationTargetException e) {
        }
        return null;
    }

    //判断Field是否是指定的类 基础了
    public static boolean isTargetClassType(Field field, Class targetType) {
        return field.getType() == targetType;
    }

    // 判断Field是否是基本类型：
    public static boolean isBasicClassType(Field field) {
        return field.getType().isPrimitive();
    }


    //public void getSubListType() throws NoSuchFieldException {
    //    //思考一下，如果我们有一个嵌套List，我们想拿到嵌套在最里面的类型，那么我们可以这么做呢？
    //    //其实我们可以使用递归的思想去获得最里面的类型
    //    Field field = Claim.class.getDeclaredField("matrix");
    //    assertEquals(getBaseType(field.getGenericType()),Double.class);
    //}
    //
    //public static Type getBaseType(Type genericReturnType){
    //    Objects.requireNonNull(genericReturnType);
    //    if (genericReturnType instanceof ParameterizedType &&
    //            List.class.isAssignableFrom((Class)(((ParameterizedType) genericReturnType).getRawType()))){
    //        Type[] actualTypeArguments = ((ParameterizedType)genericReturnType).getActualTypeArguments();
    //        Type type = actualTypeArguments[0];
    //        return getBaseType(type);
    //    }else{
    //        return genericReturnType;
    //    }
    //}

}
