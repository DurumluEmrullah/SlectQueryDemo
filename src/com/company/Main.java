package com.company;

import com.company.dao.AJoinBDao;
import com.company.dao.ExampleDao;
import com.company.dao.ExampleDao2;
import com.company.model.ExamplePojo;
import com.company.model.ExamplePojo2;
import com.company.model.dtos.AJoinBDto;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {


        AJoinBDao aJoinBDao = new AJoinBDao();
        AJoinBDto aJoinBDto = new AJoinBDto();
        aJoinBDto.setAge(1);
        aJoinBDto.setCustomerId(1);
        aJoinBDto.setSurname("aaa");
        aJoinBDto.setFirstTableId(1);
        aJoinBDto.setName("Emrul");
        aJoinBDao.getAllByNameAndSurnameAndAge(aJoinBDto,"B","C","A");




        ExampleDao exampleDao = new ExampleDao();
        ExamplePojo examplePojo = new ExamplePojo();
        examplePojo.setAge(14);
        String examplePojosByAddressAndIdAndName = exampleDao.getExamplePojosByAddressAndIdAndName(examplePojo);
        System.out.println("examplePojosByAddressAndIdAndName  =  "+examplePojosByAddressAndIdAndName);

        ExampleDao2 exampleDao2 = new ExampleDao2();

        String examplePojo2ByField1AndField2AndField3AndField7 = exampleDao2.getExamplePojo2ByField1AndField2AndField3AndField7(new ExamplePojo2());
        String examplePojo2ByField5AndField3AndField1AndField2 = exampleDao2.getExamplePojo2ByField5AndField3AndField1AndField2(new ExamplePojo2());
        String examplePojo2ByField5 = exampleDao2.getExamplePojo2ByField5(new ExamplePojo2());
        String examplePojo2ByField5AndField3AndField1AndField2AndField9AndField10 = exampleDao2.getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10(new ExamplePojo2());
        System.out.println("examplePojo2ByField1AndField2AndField3AndField7  =  "+examplePojo2ByField1AndField2AndField3AndField7);
        System.out.println("examplePojo2ByField5AndField3AndField1AndField2  =  "+examplePojo2ByField5AndField3AndField1AndField2);
        System.out.println("examplePojo2ByField5  =  "+examplePojo2ByField5);
        System.out.println("examplePojo2ByField5AndField3AndField1AndField2AndField9AndField10  =  "+examplePojo2ByField5AndField3AndField1AndField2AndField9AndField10);
    }
}
