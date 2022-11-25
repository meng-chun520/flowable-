package com.meng.flowable.flowableByPerson.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.meng.flowable.common.entity.ApplyUser;
import com.meng.flowable.common.entity.CheckUser;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.common.api.query.QueryProperty;
import org.flowable.engine.history.HistoricDetail;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.NativeHistoricVariableInstanceQuery;
import org.flowable.variable.api.persistence.entity.VariableInstance;
import org.flowable.variable.service.VariableService;
import org.flowable.variable.service.impl.HistoricVariableInstanceQueryProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.CheckedInputStream;

@RestController
@RequestMapping("/flowable")
public class AskForLeaveController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;
    @RequestMapping(value = "/list")
    public List<ApplyUser> list(String assignee) throws ParseException {
        List<ApplyUser> applyUserList = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : tasks) {
            ArrayList<CheckUser> checkUserList = new ArrayList<>();
            Map<String, Object> variables = taskService.getVariables(task.getId());
            List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().executionId(task.getExecutionId()).orderBy(new HistoricVariableInstanceQueryProperty("CREATE_TIME_")).desc().list();
            if(ObjectUtil.isNotEmpty(list)){
                Map<String, List<HistoricVariableInstance>> collectMap = list.stream().collect(Collectors.groupingBy(HistoricVariableInstance::getTaskId));
                for(Map.Entry<String,List<HistoricVariableInstance>> each : collectMap.entrySet()){
                    CheckUser checkUser = new CheckUser();
                    HashMap<String, String> hashMap = new HashMap<>();
                    List<HistoricVariableInstance> historicVariableInstanceList = each.getValue();
                    String variableName = historicVariableInstanceList.get(0).getVariableName();
                    String value = historicVariableInstanceList.get(0).getValue().toString();
                    String variableName1 = historicVariableInstanceList.get(1).getVariableName();
                    String value1 = historicVariableInstanceList.get(1).getValue().toString();
                    Date createTime = historicVariableInstanceList.get(1).getCreateTime();
                    hashMap.put(variableName,value);
                    hashMap.put(variableName1,value1);
                    checkUser.setCheckUsername(hashMap.get("checkUsername"));
                    checkUser.setCheckNotes(hashMap.get("checkNotes"));
                    checkUser.setCreateTime(createTime);
                    checkUserList.add(checkUser);

                }

            }
            variables.put("taskId",task.getId());
            variables.put("processId",task.getProcessInstanceId());
            ApplyUser applyUser = BeanUtil.toBeanIgnoreCase(variables, ApplyUser.class, false);
            checkUserList.sort(Comparator.comparing(CheckUser::getCreateTime));
            applyUser.setCheckUserList(checkUserList);
            applyUserList.add(applyUser);
        }

        return applyUserList;
    }
    @RequestMapping(value = "/apply")
    public String apply(String taskId,String checkUsername,String checkNotes,boolean isPass) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> mapLocal = new HashMap<>();
        String result = "";
        if(isPass){
            //通过审核
            map.put("outcome", "pass");
            result =  "processed ok!";
        }else {
            map.put("outcome", "back");
            result =  "reject";
        }
        mapLocal.put("checkUsername",checkUsername);
        mapLocal.put("checkNotes",checkNotes);
        taskService.setVariablesLocal(taskId,mapLocal);
        taskService.complete(taskId, map);
        return result;
    }
}
