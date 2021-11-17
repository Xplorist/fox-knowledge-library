# Java中父类对象转子类对象工具类实例

## 说明

* 静态工具类中直接调用parseDoToDTO(Object dox, Object dto)方法就行了。

* 用于mapper层查出DO对象后转换成继承自DO的DTO对象

* **核心机制**：使用java反射获取类型的属性和方法，

* aClass.getDeclaredFields() 获取本类中所有属性（不限public，不包含父类）

* aClass.getMethods() 获取本类所有public方法

## 代码

```java
package tech.xplorist.notex.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DTOConverter {

    public static Object parseDoToDTO(Object dox, Object dto) throws Exception {
        if (dox == null) {
            throw new RuntimeException("do不能为null");
        }
        if (dto == null) {
            dto = dto.getClass().newInstance();
        }

        Class<?> doxClass = dox.getClass();
        Class<?> dtoClass = dto.getClass();
        Class<?> dtoSuperclass = dtoClass.getSuperclass();
        if (doxClass != dtoSuperclass) {
            throw new RuntimeException("dto的直接父类不是do的类型");
        }

        //Method[] doxClassMethods = doxClass.getMethods();
        //Method[] dtoClassMethods = dtoClass.getMethods();

        Field[] dtoSuperclassFields = dtoSuperclass.getDeclaredFields();

        for (int i = 0; i < dtoSuperclassFields.length; i++) {
            Field field = dtoSuperclassFields[i];
            String fieldName = field.getName();
            Class<?> type = field.getType();

            if ("serialVersionUID".equals(fieldName)) {
                continue;
            }

            String s = String.valueOf(fieldName.charAt(0));
            String s1 = fieldName.replaceFirst(s, s.toUpperCase());

            String getMethodName = "get" + s1;
            String setMethodName = "set" + s1;

            Method getMethod = doxClass.getMethod(getMethodName, null);
            Object getMethodResult = getMethod.invoke(dox);

            Method setMethod = dtoClass.getMethod(setMethodName, type);
            setMethod.invoke(dto, getMethodResult);
        }

        return dto;
    }

    public static Object parseDoToDTOx(Object dox, Object dto) throws Exception {
        if (dox == null) {
            throw new RuntimeException("do不能为null");
        }
        if (dto == null) {
            dto = dto.getClass().newInstance();
        }

        Class<?> doClass = dox.getClass();
        Field[] doFields = doClass.getFields();

        Class<?> dtoClass = dto.getClass();
        Field[] dtoFields = dtoClass.getFields();

        accessClassFieldsAndMethods(dto);

        for (int i = 0; i < doFields.length; i++) {
            Field doField =doFields[i];
            String doFieldName = doField.getName();

            for (int j = 0; j < dtoFields.length; j++) {
                Field dtoField = dtoFields[j];
                String dtoFieldName = dtoField.getName();
                if (doFieldName.equals(dtoFieldName)) {
                    char c = doFieldName.charAt(0);
                    String s = String.valueOf(c);
                    String s1 = doFieldName.replaceFirst(s, s.toUpperCase());
                    s1 = "set" + s1;

                    Class<?> type = dtoField.getType();
                    Method dtoClassMethod = dtoClass.getMethod(s1, type);

                    doField.setAccessible(true);
                    Object o = doField.get(dox);
                    dtoClassMethod.invoke(dto, o);
                }
            }
        }

        return dto;
    }

    private static void accessClassFieldsAndMethods(Object obj) {
        Class<?> objClass = obj.getClass();

        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }

        Method[] methods = objClass.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
        }

        Class<?> superclass = objClass.getSuperclass();
        if (superclass != Object.class) {
            accessClassFieldsAndMethods(superclass);
        }
    }

}


```