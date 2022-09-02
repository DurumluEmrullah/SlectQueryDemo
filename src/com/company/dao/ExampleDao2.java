package com.company.dao;

import com.company.annotations.Query;
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

    @Query(extraConditions = "FIELD_1 IN(?,?)")
    public String getExamplePojo2ByField5AndField3AndField1AndField2AndField9AndField10(ExamplePojo2 examplePojo2,String field1Type1,String field1Type2){
        return select(examplePojo2,field1Type1,field1Type2);
    }
}
