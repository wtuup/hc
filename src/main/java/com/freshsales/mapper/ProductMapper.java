package com.freshsales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.freshsales.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    @Select("SELECT * FROM product ORDER BY sales DESC LIMIT #{limit}")
    List<Product> selectTopBySales(int limit);
}