package com.company.dao;

import com.company.model.ExamplePojo;

public class ExampleDao extends BaseDao<ExamplePojo>{

    public String getExamplePojosByAddress(ExamplePojo examplePojo){
        return select(examplePojo);
    }

    public String getExamplePojosByAddressAndIdAndName(ExamplePojo examplePojo){
        return select(examplePojo);
    }
}
