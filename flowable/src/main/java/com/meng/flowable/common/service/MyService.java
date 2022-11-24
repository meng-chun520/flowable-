package com.meng.flowable.common.service;

import com.meng.flowable.common.entity.Student;
import com.meng.flowable.common.mapper.StudentMapper;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class MyService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private StudentMapper studentMapper;
    public String addExpense(Long applyUserId,String applyUsername,String reason){
        //对输入的学号和姓名进行校验
        if(applyUserId == null || applyUsername == null){
            return "用户名或者学号不能为空";
        }
        Student student = studentMapper.selectById(applyUserId);
        if(!applyUsername.equals(student.getName())){
            return "用户名与学号不匹配，请重新输入";
        }
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("applyUserId", applyUserId);
        map.put("applyUsername", applyUsername);
        map.put("reason", reason);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("askForLeave", map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }
}
