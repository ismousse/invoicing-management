package com.casual.vo;

import com.casual.entity.Good;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodVo implements Serializable {

    private String goodid;

    private String unitid;

    private String goodname;

    @ApiModelProperty(value = "单位名称")
    private String unitname;
}
