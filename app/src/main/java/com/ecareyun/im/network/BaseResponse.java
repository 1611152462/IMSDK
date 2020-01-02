package com.ecareyun.im.network;

/**
 * 项目名称：CloudPos
 * 类描述：
 * 创建人：xiezheng
 * 创建时间：2018/12/19 上午11:20
 * 修改人：xiezheng
 * 修改时间：2018/12/19 上午11:20
 * 修改备注：
 */
public class BaseResponse<T> {

    /**
     * status : 0
     * errorcode : 7019
     * msg :
     */

    private String status;
    private String errorcode;
    private String msg;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
