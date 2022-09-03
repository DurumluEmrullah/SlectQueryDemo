package com.company.dao;

import com.company.annotations.Query;
import com.company.model.dtos.AJoinBDto;

import java.util.List;

public class AJoinBDao extends BaseJoinDao<AJoinBDto>{


    @Query(extraConditions = "A.CUSTOMER_TYPE IN (?, ? ,?)")
    public List<AJoinBDto> getAllByNameAndSurnameAndAge(AJoinBDto aJoinBDto, String firstTypeCustomer, String secondTypeCustomer, String thirdTypeCustomer){
        return select(aJoinBDto,firstTypeCustomer,secondTypeCustomer,thirdTypeCustomer);
    }

}
