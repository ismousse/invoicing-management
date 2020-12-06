package com.casual.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private String goodid;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "库存量")
    private Integer count;
}
