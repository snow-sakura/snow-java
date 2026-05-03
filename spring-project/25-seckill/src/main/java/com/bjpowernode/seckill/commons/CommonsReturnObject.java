package com.bjpowernode.seckill.commons;

import java.io.Serializable;

/**
 * ClassName:CommonsReturnObject
 * Package:com.bjpowernode.seckill.commons
 * Description:
 *
 * @date:2018/10/25 11:15
 * @author:bjpowernode.com
 */
public class CommonsReturnObject implements Serializable {
    private static final long serialVersionUID = 326257344007822118L;

    private String errorCode;

    private String errorMessage;

    private Object data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
