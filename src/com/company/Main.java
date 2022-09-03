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
        String allByNameAndSurnameAndAge = aJoinBDao.getAllByNameAndSurnameAndAge(aJoinBDto, "B", "C", "A");
        System.out.println("getAllByNameAndSurnameAndAge : "+allByNameAndSurnameAndAge);


        ExampleDao exampleDao = new ExampleDao();
        ExamplePojo examplePojo = new ExamplePojo();
        examplePojo.setAge(14);
        exampleDao.getExamplePojosByAddressAndIdAndName(examplePojo);
     //   System.out.println("examplePojosByAddressAndIdAndName  =  "+examplePojosByAddressAndIdAndName);

        ExampleDao2 exampleDao2 = new ExampleDao2();

        exampleDao2.getExamplePojo2ByField1AndField2AndField3AndField7(new ExamplePojo2());
        exampleDao2.getExamplePojo2ByField5AndField3AndField1AndField2(new ExamplePojo2());
         exampleDao2.getExamplePojo2ByField5(new ExamplePojo2());
        exampleDao2.getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10(new ExamplePojo2(),"type_1","type_2");
//        System.out.println("examplePojo2ByField1AndField2AndField3AndField7  =  "+examplePojo2ByField1AndField2AndField3AndField7);
//        System.out.println("examplePojo2ByField5AndField3AndField1AndField2  =  "+examplePojo2ByField5AndField3AndField1AndField2);
//        System.out.println("examplePojo2ByField5  =  "+examplePojo2ByField5);
//        System.out.println("examplePojo2ByField5AndField3AndField1AndField2AndField9AndField10  =  "+examplePojo2ByField5AndField3AndField1AndField2AndField9AndField10);
    }
}
