package com.company.dao;

import com.company.annotations.Query;
import com.company.annotations.dto.JoinColumn;
import com.company.annotations.dto.JoinTable;



import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import java.util.Locale;

import java.util.List;

public class BaseJoinDao <E>{

    public List<E> select(E e,Object ... args){

        StringBuffer query = new StringBuffer();
        query.append("Select ");


        String[] whereConditions;
        List<String> where = new ArrayList<>();
        List values = new ArrayList();
        String methodName="";
        Method[] subClassMethods=null;

        String leftTableName="";
        String rightTableName="";
        String leftTableAlias ="";
        String rightTableAlias = "";


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

        Annotation[] dtoDeclaredAnnotations = tClass.getDeclaredAnnotations();

        for (Annotation dtoDeclaredAnnotation : dtoDeclaredAnnotations) {
            if(dtoDeclaredAnnotation instanceof  JoinTable){
                leftTableName= ((JoinTable)dtoDeclaredAnnotation).leftTableName();
                leftTableAlias= ((JoinTable)dtoDeclaredAnnotation).leftTableAlias();
                rightTableName= ((JoinTable)dtoDeclaredAnnotation).rightTableName();
                rightTableAlias= ((JoinTable)dtoDeclaredAnnotation).rightTableAlias();
            }
        }

        Field[] declaredFields = tClass.getDeclaredFields();




        for (Field declaredField : declaredFields) {
            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if (declaredAnnotation instanceof JoinColumn) {

                    if(((JoinColumn)declaredAnnotation).isLeft()){
                        query.append(leftTableAlias).append(".").append(((JoinColumn)declaredAnnotation).columnName().trim()).append(", ");

                    }else{
                        query.append(rightTableAlias).append(".").append(((JoinColumn)declaredAnnotation).columnName().trim()).append(", ");
                    }
                }
            }
        }

        query.delete(query.toString().lastIndexOf(","),query.toString().length());

        query.append(" FROM ")
                .append(leftTableName).append(" ").append(leftTableAlias).append(" INNER JOIN ").append(rightTableName).append(" ").append(rightTableAlias).append(" ON ");




        for (Field declaredField : declaredFields) {
            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if(declaredAnnotation instanceof JoinColumn){
                    if(!((JoinColumn)declaredAnnotation).joinColumnName().equals("")){
                        if(((JoinColumn)declaredAnnotation).isLeft()){
                            query.append(leftTableAlias)
                                    .append(".")
                                    .append(((JoinColumn)declaredAnnotation).columnName())
                                    .append("=")
                                    .append(rightTableAlias)
                                    .append(".")
                                    .append(((JoinColumn)declaredAnnotation).joinColumnName())
                                    .append(" AND ");
                        }
                        else {
                            query
                                    .append(rightTableAlias)
                                    .append(".")
                                    .append(((JoinColumn)declaredAnnotation).columnName())
                                    .append("=")
                                    .append(leftTableAlias)
                                    .append(".")
                                    .append(((JoinColumn)declaredAnnotation).joinColumnName())
                                    .append(" AND ");
                        }
                    }
                }
            }
        }


        query.delete(query.toString().lastIndexOf("AND"),query.toString().length());



        if(whereConditions.length>0){
            query.append(" WHERE ");

            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);

                for (String whereCondition : whereConditions) {
                    if(declaredField.getName().toLowerCase(Locale.ROOT).trim().equals(whereCondition.toLowerCase(Locale.ROOT).trim())){
                        try {
                            Annotation[] declaredAnnotations1 = declaredField.getDeclaredAnnotations();
                            for (Annotation annotation : declaredAnnotations1) {
                                if(annotation instanceof JoinColumn){

                                    if(((JoinColumn)annotation).isLeft()){
                                        where.add(leftTableAlias+"."+((JoinColumn)annotation).columnName());
                                        values.add(declaredField.get(e));

                                    }
                                    else{
                                        where.add(rightTableAlias+"."+((JoinColumn)annotation).columnName());
                                        values.add(declaredField.get(e));
                                    }

                                }
                            }


                        } catch (IllegalAccessException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                    }

                }


            }
        }

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
