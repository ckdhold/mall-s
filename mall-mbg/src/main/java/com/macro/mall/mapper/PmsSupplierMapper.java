package com.macro.mall.mapper;

import com.macro.mall.model.PmsSupplier;
import com.macro.mall.model.PmsSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsSupplierMapper {
    long countByExample(PmsSupplierExample example);

    int deleteByExample(PmsSupplierExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsSupplier row);

    int insertSelective(PmsSupplier row);

    List<PmsSupplier> selectByExample(PmsSupplierExample example);

    PmsSupplier selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") PmsSupplier row, @Param("example") PmsSupplierExample example);

    int updateByExample(@Param("row") PmsSupplier row, @Param("example") PmsSupplierExample example);

    int updateByPrimaryKeySelective(PmsSupplier row);

    int updateByPrimaryKey(PmsSupplier row);
}
