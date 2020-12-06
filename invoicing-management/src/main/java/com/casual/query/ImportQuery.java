package com.casual.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Import查询对象", description = "进货查询对象封装")
@Data
public class ImportQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private String goodid;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "操作人员")
    private String username;

    @ApiModelProperty(value = "开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
