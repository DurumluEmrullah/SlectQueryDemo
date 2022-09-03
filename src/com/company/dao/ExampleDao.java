package com.company.dao;

import com.company.model.ExamplePojo;
import java.util.List;

public class ExampleDao extends BaseDao<ExamplePojo>{

    public List<ExamplePojo> getExamplePojosByAddress(ExamplePojo examplePojo){
        return select(examplePojo);
    }

    public List<ExamplePojo> getExamplePojosByAddressAndIdAndName(ExamplePojo examplePojo){
        return select(examplePojo);
    }
}
