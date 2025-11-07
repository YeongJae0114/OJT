package org.example.transcation.week4.exapmle.mapper;

import org.apache.ibatis.annotations.*;
import org.example.transcation.week4.exapmle.domain.PrimaryTable;
import org.example.transcation.week4.exapmle.domain.SecondaryTable;
import org.example.transcation.week4.exapmle.dto.TestDto;
import org.example.transcation.week4.exapmle.dto.UpdateDto;

import java.util.List;

@Mapper
public interface TestMapper {
    @Insert("INSERT INTO primary_table (key_value, first_value) VALUES (#{keyValue}, #{firstValue})")
    void insertPrimary(TestDto dto);

    @Insert("INSERT INTO secondary_table (key_value, second_value) VALUES (#{keyValue}, #{secondValue})")
    void insertSecondary(TestDto dto);

    // UPDATE
    @Update("UPDATE primary_table SET first_value = #{firstValue} WHERE id = #{id}")
    int updatePrimaryByParam(@Param("id") Long id, @Param("firstValue") String firstValue);

    @Update("UPDATE secondary_table SET second_value = #{secondValue} WHERE id = #{id}")
    int updateSecondaryByParam(@Param("id") Long id, @Param("secondValue") String secondValue);


    @Select("SELECT id, key_value AS keyValue, first_value AS firstValue FROM primary_table")
    List<PrimaryTable> findAllFirst();

    @Select("SELECT id, key_value AS keyValue, second_value AS secondValue FROM secondary_table")
    List<SecondaryTable> findAllSecond();

}
