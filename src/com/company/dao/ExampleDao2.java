package com.company.dao;

import com.company.model.ExamplePojo2;

public class ExampleDao2 extends BaseDao<ExamplePojo2> {

    public String getExamplePojo2ByField1AndField2AndField3AndField7(ExamplePojo2 examplePojo2){
        return select(examplePojo2);
    }

    public String getExamplePojo2ByField5AndField3AndField1AndField2(ExamplePojo2 examplePojo2){
        return select(examplePojo2);
    }

    public String getExamplePojo2ByField5(ExamplePojo2 examplePojo2){
        return select(examplePojo2);
    }

    public String getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10(ExamplePojo2 examplePojo2){
        return select(examplePojo2);
    }
}
