package com.company.model;

import com.company.annotations.pojo.Column;
import com.company.annotations.pojo.Table;

@Table(name="Example_Table")
public class ExamplePojo {

    @Column(name="ID")
    private Integer id;

    @Column(name="NAME")
    private String name;

    @Column(name="SURNAME")
    private String surname;

    @Column(name="AGE")
    private Integer age;

    @Column(name="ADDRES")
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
