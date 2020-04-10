package com.nuc.mapper;

import com.nuc.pojo.Search;
import com.nuc.pojo.SearchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SearchMapper {
    long countByExample(SearchExample example);

    int deleteByExample(SearchExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Search record);

    int insertSelective(Search record);

    List<Search> selectByExample(SearchExample example);

    Search selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Search record, @Param("example") SearchExample example);

    int updateByExample(@Param("record") Search record, @Param("example") SearchExample example);

    int updateByPrimaryKeySelective(Search record);

    int updateByPrimaryKey(Search record);

	int recommend(String uid);
}