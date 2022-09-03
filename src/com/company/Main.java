package com.company;

import com.company.dao.AJoinBDao;
import com.company.dao.ExampleDao;
import com.company.dao.ExampleDao2;
import com.company.model.ExamplePojo;
import com.company.model.ExamplePojo2;
import com.company.model.dtos.AJoinBDto;


public class Main {

    public static void main(String[] args) {


        AJoinBDao aJoinBDao = new AJoinBDao();
        AJoinBDto aJoinBDto = new AJoinBDto();
        aJoinBDto.setAge(1);
        aJoinBDto.setCustomerId(1);
        aJoinBDto.setSurname("aaa");
        aJoinBDto.setFirstTableId(1);
        aJoinBDto.setName("Emrul");

        System.out.println("AJoinBDto#getAllByNameAndSurnameAndAge \n ");

        aJoinBDao.getAllByNameAndSurnameAndAge(aJoinBDto, "B", "C", "A");

        System.out.println();

        System.out.println("-------------------------------------------------------");
        System.out.println("ExampleDao#getExamplePojosByAddressAndIdAndName \n");

        ExampleDao exampleDao = new ExampleDao();
        ExamplePojo examplePojo = new ExamplePojo();
        examplePojo.setAddress("Istanbul");
        examplePojo.setName("Emrul");
        examplePojo.setId(1);


        exampleDao.getExamplePojosByAddressAndIdAndName(examplePojo);

        System.out.println();

        System.out.println("-------------------------------------------------------");

        System.out.println("ExampleDao2#getExamplePojo2ByField1AndField2AndField3AndField7 \n");
        ExampleDao2 exampleDao2 = new ExampleDao2();
        ExamplePojo2 examplePojo2 = new ExamplePojo2();
        examplePojo2.setField1(1);
        examplePojo2.setField2(2);
        examplePojo2.setField3(3);
        examplePojo2.setField7(4);
        exampleDao2.getExamplePojo2ByField1AndField2AndField3AndField7(examplePojo2);

        System.out.println();

        System.out.println("-------------------------------------------------------");

        System.out.println("ExampleDao2#getExamplePojo2ByField5AndField3AndField1AndField2 \n");

        ExamplePojo2 example = new ExamplePojo2();
        example.setField5(1);
        example.setField3(2);
        example.setField1(3);
        example.setField2(4);
        exampleDao2.getExamplePojo2ByField5AndField3AndField1AndField2(example);


        System.out.println();

        System.out.println("-------------------------------------------------------");

        System.out.println("ExampleDao2#getExamplePojo2ByField5 \n");

        ExamplePojo2 example2 = new ExamplePojo2();
        example2.setField5(1);
        exampleDao2.getExamplePojo2ByField5(example2);


        System.out.println();

        System.out.println("-------------------------------------------------------");


        System.out.println("ExampleDao2#getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10 \n");
        ExamplePojo2 example3 = new ExamplePojo2();
        example3.setField5(1);
        example3.setField3(2);
        example3.setField1(3);
        example3.setField2(4);
        example3.setField9(5);
        example3.setField10(6);
        exampleDao2.getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10(example3, "type_1", "type_2");

    }
}
