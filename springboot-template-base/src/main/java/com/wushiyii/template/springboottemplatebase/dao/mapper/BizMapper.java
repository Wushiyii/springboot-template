package com.wushiyii.template.springboottemplatebase.dao.mapper;


import com.wushiyii.template.springboottemplatebase.dao.mapper.plugin.InsertNotNullLangDriver;
import com.wushiyii.template.springboottemplatebase.dao.mapper.plugin.SelectInLangDriver;
import com.wushiyii.template.springboottemplatebase.dao.mapper.plugin.UpdateNotNullLangDriver;
import com.wushiyii.template.springboottemplatebase.model.domain.SampleDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BizMapper {

    /**
     * 单条数据插入
     * @param sampleDO
     * @return
     */
    @Insert("insert into `sample`(#{sampleDO})")
    @Lang(InsertNotNullLangDriver.class)
    Integer insert(SampleDO sampleDO);

    /**
     * 单条数据更新
     * @param sampleDO
     * @return
     */
    @Update("update `sample`(#{sampleDO}) where id = #{id}")
    @Lang(UpdateNotNullLangDriver.class)
    Integer update(SampleDO sampleDO);

    /**
     * 根据ID查询单条数据
     * @param id
     * @return
     */
    @Select("select * from `sample` where id = #{id}")
    SampleDO selectById(Integer id);

    /**
     * 根据key执行in查询
     * @param keys
     * @return
     */
    @Select("select * from `sample` where name in(#{keys})")
    @Lang(SelectInLangDriver.class)
    List<SampleDO> selectByKeys(@Param("keys") List<String> keys);
}
