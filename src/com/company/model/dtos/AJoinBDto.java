package com.company.model.dtos;


import com.company.annotations.dto.JoinColumn;
import com.company.annotations.dto.JoinTable;

@JoinTable(leftTableName = "FirstTable",
        leftTableAlias = "A",
        rightTableName = "SecondTable",
        rightTableAlias = "B")
public class AJoinBDto {

    @JoinColumn(isLeft = true,columnName = "FIRST_TABLE_ID",joinColumnName = "SECOND_TABLE_ID")
    private Integer firstTableId;

    @JoinColumn(isLeft = false,columnName = "CUSTOMER_ID", joinColumnName = "CUSTOMER_ID")
    private Integer customerId;

    @JoinColumn(isLeft = true,columnName = "NAME")
    private String name;

    @JoinColumn(isLeft = false,columnName = "SURNAME")
    private String surname;

    @JoinColumn(isLeft = false,columnName = "AGE")
    private Integer age;

    public Integer getFirstTableId() {
        return firstTableId;
    }

    public void setFirstTableId(Integer firstTableId) {
        this.firstTableId = firstTableId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
