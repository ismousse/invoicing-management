package com.casual.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ImportVo {
    private String exportid;
    private String goodid;
    private Integer count;
    private Date created;
    private String username;
    private String goodname;
}
