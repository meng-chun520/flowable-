package com.meng.flowable.common.entity;

import lombok.Data;

@Data
public class ApplyUserParam {
    private Long applyUserId;
    private String applyUsername;
    private String reason;
    private Long counselor;
    private Long vicePresident;
}
