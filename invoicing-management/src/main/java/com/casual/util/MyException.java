package com.casual.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "全局异常")
public class MyException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public MyException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public MyException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "MyException{" + "message=" + this.getMessage() + ", code=" + code + '}';
    }
}
