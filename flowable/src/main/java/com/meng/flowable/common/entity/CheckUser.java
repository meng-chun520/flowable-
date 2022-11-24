package com.meng.flowable.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CheckUser {
     private String checkUsername;
     private String checkNotes;
     private Date createTime;
}
