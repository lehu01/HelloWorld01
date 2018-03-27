package com.zmsoft.ccd.lib.base.bean;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/5 15:27
 */
public class HttpResult<T> {

    public static final int SUCCESS = 1; // 成功
    public static final int FAILURE = 0; // 失败

    /**
     * 数据对象，标准json格式
     */
    private T data;
    /**
     * 接口调用是否成功，0：失败，1：成功
     * <p>
     * <b>这个code既代表了客户端有没有调通接口、也代表了服务端业务处理有没有成功<br />
     * 如果调通了接口，但是服务端业务处理失败了（比如购物车清理失败）该code的值仍是0
     * </b>
     * </p>
     */
    private int code;
    /**
     * 业务编码 接口调用错误业务编码，具体编码。格式为ERR_XXX000，其中ERR_为固定前缀；XXX为平台简称，
     * 比如交易平台为TCS；000为平台内部返回码编号；ERR_PUB000为公共返回码，000为可变数据部分。编码定义链接
     */
    private String errorCode;
    /**
     * 错误消息内容，国际化项目后根据请求的语言类型由API层返回不同语言的消息内容
     */
    private String message;
    /**
     * 记录条数，当查询列表时接口返回的记录条数
     */
    private int record;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }
}
