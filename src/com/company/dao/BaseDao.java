package com.company.dao;

import com.company.annotations.pojo.Column;
import com.company.annotations.pojo.Table;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BaseDao<E> {



    public String select(E e){
        String query="Select ";
        String[] whereConditions;
        Map<String,Object> where = new HashMap<>();
        String methodName="";
        String tableName="";
        try {
            throw new Exception();
        }catch (Exception exception){
            StackTraceElement[] stackTrace = exception.getStackTrace();
            methodName = stackTrace[1].getMethodName();

        }

        if(methodName.contains("By")){

            int by = methodName.lastIndexOf("By");
            methodName = methodName.substring(by+2);
            whereConditions = methodName.split("And");
        }
        else{
            whereConditions = new String[]{};
        }



        Class<E> tClass =(Class<E>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];

        Field[] declaredFields =
                tClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if(declaredAnnotation.annotationType().getName().trim().equals("com.company.annotations.pojo.Column")){
                    query +=((Column)declaredAnnotation).name() +" ,";
                }
            }
//// V2
//            System.out.println(declaredField.getName().toString());
//            try {
//                declaredField.setAccessible(true);
//                Object o = declaredField.get(e);
//
//                if(o != null){
//                    where.put(declaredField.getName(),o);
//                }
//
//                System.out.println(o);
//            } catch (IllegalAccessException illegalAccessException) {
//                illegalAccessException.printStackTrace();
//            }



        }

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if(whereConditions !=null){
                for (String whereCondition : whereConditions) {
                    if(declaredField.getName().toLowerCase(Locale.ROOT).trim().equals(whereCondition.toLowerCase(Locale.ROOT).trim())){
                        try {
                            Annotation[] declaredAnnotations1 = declaredField.getDeclaredAnnotations();
                            for (Annotation annotation : declaredAnnotations1) {
                                if(annotation.annotationType().getName().trim().equals("com.company.annotations.pojo.Column")){
                                    where.put(((Column)annotation).name(),declaredField.get(e));
                                }
                            }


                        } catch (IllegalAccessException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                    }

                }
            }
        }
        int lastCamma = query.lastIndexOf(",");
        query = query.substring(0,lastCamma);

        query +="FROM ";


        Annotation[] declaredAnnotations = tClass.getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if(declaredAnnotation.annotationType().getName().trim().equals("com.company.annotations.pojo.Table")){
                tableName=((Table)declaredAnnotation).name();
            }
        }

        query +=tableName+" WHERE ";

        for (Map.Entry<String, Object> stringObjectEntry : where.entrySet()) {
            query +=stringObjectEntry.getKey()+"="+stringObjectEntry.getValue()+" AND ";
        }

        int and = query.lastIndexOf("AND");



        return query.substring(0,and);
    }


}
