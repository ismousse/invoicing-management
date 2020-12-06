package com.casual.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "商品查询对象", description = "商品查询对象封装")
@Data
public class GoodQuery {
    @ApiModelProperty(value = "商品id")
    private String id;

    @ApiModelProperty(value = "商品名称")
    private String name;
}
