package com.casual.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockVo implements Serializable {
    private String stockid;
    private String goodid;
    private String goodname;
    private Integer count;
}
