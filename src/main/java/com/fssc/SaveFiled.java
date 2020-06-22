package com.fssc;

import com.common.BaseDto;
import com.common.Cat;
import com.common.Person;
import com.util.DtoRefUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import static com.util.DtoRefUtil.fmtDate;
import static com.util.DtoRefUtil.parGetName;

public class SaveFiled {



    public static void main(String[] args) {
        ClaimTmpl tmpl = new ClaimTmpl();
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
        claim.setAmount(new BigDecimal(7.77));
        Cat cat = new Cat("lala");
        claim.setCat(cat);
        List<Snapshot> list = new ArrayList<>();
        Snapshot ss = new Snapshot();
        ss.setRegionCode("claim");

        List<Cat> cats = new ArrayList<>();
        Cat cat2 = new Cat("DoDo");
        Cat cat3 = new Cat("SOl");
        Cat cat4 = new Cat("RE");
        cats.add(cat2);
        cats.add(cat3);
        cats.add(cat4);
        tmpl.setClaim(claim);
        tmpl.setCats(cats);
        peoples.add(p3);
        tmpl.setId((long) 10001);
        tmpl.setName("prePAL");

        getFiled(tmpl, list, ss);
        System.out.println(list.size());
        System.out.println(list);

        Map<String, String> map = DtoRefUtil.getFieldValueMap(tmpl);

        System.out.println(map.toString());
    }

    public static void getFiled(BaseDto object, List<Snapshot> list, Snapshot ss) {

        Field[] fields = object.getClass().getDeclaredFields();
        Map<Object, Object> map = new HashMap<>();
        for (Field field : fields) {
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
                getFiled((BaseDto)value, list, ss);
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
    public static void getFiledList(List<BaseDto> object, List<Snapshot> list, Snapshot ss) {

        Long num = Long.valueOf(1);
        for (BaseDto o : object) {
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
            //if ("Date".equals(fieldType)) {
            //    value = fmtDate((Date) value);
            //}
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
