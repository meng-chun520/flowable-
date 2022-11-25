package com.meng.flowable.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CheckUser {
     private String checkUsername;
     private String checkNotes;
     @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
     private Date createTime;
}
