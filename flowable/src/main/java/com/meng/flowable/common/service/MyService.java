package com.meng.flowable.common.service;

import com.meng.flowable.common.entity.ApplyUserParam;
import com.meng.flowable.common.entity.SysUser;
import com.meng.flowable.common.mapper.SysUserMapper;
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
    private SysUserMapper sysUserMapper;
    public String add(ApplyUserParam applyUserParam){
        //对输入的学号和姓名进行校验
        if(applyUserParam.getApplyUserId() == null || applyUserParam.getApplyUsername() == null){
            return "用户名或者学号不能为空";
        }
        SysUser student = sysUserMapper.selectById(applyUserParam.getApplyUserId());
        if(!applyUserParam.getApplyUsername().equals(student.getName())){
            return "用户名与学号不匹配，请重新输入";
        }
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("applyUsername", applyUserParam.getApplyUsername());
        map.put("reason", applyUserParam.getReason());
        map.put("applyUserId", applyUserParam.getApplyUserId());
        map.put("counselor",applyUserParam.getCounselor());
        map.put("vicePresident",applyUserParam.getVicePresident());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("askForLeave", map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }
}
