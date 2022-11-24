package com.meng.flowable.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.flowable.common.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
