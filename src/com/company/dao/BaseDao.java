package com.company.dao;

import com.company.annotations.Query;
import com.company.annotations.pojo.Column;
import com.company.annotations.pojo.Table;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class BaseDao<E> {

    public List<E> select(E e,Object ... args){
        StringBuffer query = new StringBuffer();
        query.append("Select ");
        String[] whereConditions;

        Method[] subClassMethods=null;

        List<String> where = new ArrayList<>();
        List values = new ArrayList();


        String methodName="";
        String tableName="";


        try {
            throw new Exception();
        }catch (Exception exception){
            StackTraceElement[] stackTrace = exception.getStackTrace();
            methodName = stackTrace[1].getMethodName();
            try {
                Class<?> subClass = Class.forName(stackTrace[1].getClassName());
                subClassMethods= subClass.getMethods();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }

        if(methodName.contains("By")){

            int by = methodName.lastIndexOf("By");
            String whereConditionsString = methodName.substring(by+2);
            whereConditions = whereConditionsString.split("And");
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
                if(declaredAnnotation instanceof Column){
                    query.append(((Column)declaredAnnotation).name()).append(" ,");

                }
            }

        }

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if(whereConditions.length>0){
                for (String whereCondition : whereConditions) {
                    if(declaredField.getName().toLowerCase(Locale.ROOT).trim().equals(whereCondition.toLowerCase(Locale.ROOT).trim())){
                        try {
                            Annotation[] declaredAnnotations1 = declaredField.getDeclaredAnnotations();
                            for (Annotation annotation : declaredAnnotations1) {
                                if(annotation instanceof Column){

                                    where.add(((Column)annotation).name());
                                    values.add(declaredField.get(e));

                                }
                            }


                        } catch (IllegalAccessException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                    }

                }
            }
        }
        query.delete(query.toString().lastIndexOf(","),query.toString().length());

        query.append(" FROM ");


        Annotation[] declaredAnnotations = tClass.getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if(declaredAnnotation instanceof Table){
                tableName=((Table)declaredAnnotation).name();
            }
        }

        query.append(tableName).append(" WHERE ");

        for (String whereCondition : where) {
            query.append(" ").append(whereCondition).append(" = ? AND ");
        }

        query.delete(query.toString().lastIndexOf("AND"),query.toString().length());

        for (Method subClassMethod : subClassMethods) {
            if(subClassMethod.getName().trim().equals(methodName.trim())){
                Annotation[] subClassDeclaredAnnotations = subClassMethod.getDeclaredAnnotations();
                for (Annotation declaredAnnotation : subClassDeclaredAnnotations) {
                    if(declaredAnnotation instanceof Query){

                        String[] extraConditions = ((Query) declaredAnnotation).extraConditions();

                        for (String extraCondition : extraConditions) {
                            query.append(" AND ").append(extraCondition);
                        }

                        for (Object arg : args) {
                            values.add(arg);
                        }
                    }

                }
            }
        }

        // Jdbc template bu kısmda kullanılacak.
        System.out.println("Oluşturulan sorgu : "+query.toString());
        System.out.println("Sorgunun parametreleri : "+values);


        return null;
    }


}
