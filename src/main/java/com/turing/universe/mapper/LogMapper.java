package com.turing.universe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turing.universe.entity.Book;
import com.turing.universe.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-02-11
 * @see
 * @since
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {
    @Select("select question,parsetype from platform_cloud_log_202208 where id>=${idStart} and id< ${idEnd}")
    List<Log> getLogsById(@Param("idStart") Long idStart, @Param("idEnd") Long idEnd);

    @Select("select question,parsetype from platform_cloud_log_202208 where create_date>${start} and create_date < ${end}")
    List<Log> getLogsByDate(@Param("start") String start, @Param("end") String end);
}
