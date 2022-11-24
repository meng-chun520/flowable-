package com.meng.flowable.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class ApplyUser {
    private String taskId;
    private Long applyUserId;
    private String reason;
    private String applyUsername;
    private List<CheckUser> checkUserList;
}
