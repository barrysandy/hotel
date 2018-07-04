package com.zzk.util;


import java.io.Serializable;

/** * Created by Administrator on 2016/9/2.

 */

public class Result<T> implements Serializable{

    private int state;//状态 0、1

    private String msg;//成功-失败信息 success error
    
    private String message;//自定义消息  eg: 请求超时。。。。。。。。

    private T data;
    
    public Result() {
    }
    /**
     * <p>description:操作成功</p>
     * @return Result
     * @author Wen Yugang
     * @date 2017-3-20下午2:38:13
     */
    public static Result success(){
    	Result<Object> result = new Result<Object>();
    	result.setState(1);
    	result.setMsg("success");
    	result.setMessage("操作成功！");
    	return result;
    }
    /**
     * <p>description:操作失败</p>
     * @return Result
     * @author Wen Yugang
     * @date 2017-3-20下午2:38:38
     */
    public static Result error(String message){
    	Result result = new Result();
    	result.setState(0);
    	result.setMsg("error");
    	result.setMessage(message);
    	return result;
    }
    /**
     * <p>description:绑定返回数据</p>
     * @return Result
     * @author Wen Yugang
     * @date 2017-3-20下午2:37:50
     */
    public Result data(T t){
    	this.data = t;
    	return this;
    }
    /**
     * <p>description:自定义返回信息</p>
     * @return Result
     * @author Wen Yugang
     * @date 2017-3-20下午2:39:57
     */
    public Result message(String message){
    	this.message = message;
    	return this;
    }
    
    public Result(T data) {
        this.state = 1;
        this.msg = "success";
        this.message = "操作成功";
        this.data = data;
    }
    public Result(int state,String msg){
        this.state = state;
        this.msg = msg;
    }
    public Result(int state, String msg,String message) {
        this.message = message;
        this.msg = msg;
        this.state = state;
    }
    public Result(int state,String msg,String message,T data){
        this.state = state;
        this.msg = msg;
        this.message = message;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("state=").append(state);
        sb.append(",message='").append(message).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
