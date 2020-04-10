package com.nuc.mapper;

import com.nuc.pojo.Cartitem;
import com.nuc.pojo.CartitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartitemMapper {
    long countByExample(CartitemExample example);

    int deleteByExample(CartitemExample example);

    int deleteByPrimaryKey(String cartItemId);

    int insert(Cartitem record);

    int insertSelective(Cartitem record);

    List<Cartitem> selectByExample(CartitemExample example);

    Cartitem selectByPrimaryKey(String cartItemId);

    int updateByExampleSelective(@Param("record") Cartitem record, @Param("example") CartitemExample example);

    int updateByExample(@Param("record") Cartitem record, @Param("example") CartitemExample example);

    int updateByPrimaryKeySelective(Cartitem record);

    int updateByPrimaryKey(Cartitem record);
}