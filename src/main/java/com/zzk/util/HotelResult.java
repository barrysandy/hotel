package com.zzk.util;

import java.io.Serializable;

/** * Created by Administrator on 2016/9/2.

 */

public class HotelResult<T> implements Serializable{

    private int state;//状态 0、1

    private String msg;//成功-失败信息 success error
    
    private String message;//自定义消息         请求超时。。。。。。。。

    private T data;
    //页号
    private int pageIndex;
    //页大小
    private int pageSize;
    //总数据量
    private int totalNum;
    //页数
    private int pageCount = 0;
    
    public HotelResult() {
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
    public static Result error(){
    	Result result = new Result();
    	result.setState(0);
    	result.setMsg("error");
    	result.setMessage("操作失败！");
    	return result;
    }
    
    
    public HotelResult(int state,String msg,String message,T data){
        this.state = state;
        this.msg = msg;
        this.message = message;
        this.data = data;
    }
    
    public HotelResult(T data) {
        this.state = 1;
        this.msg = "success";
        this.data = data;
    }
    public HotelResult(T data, Integer pageIndex, Integer pageSize, Integer totalNum) {
        this(data);
        this.pageIndex=pageIndex;
        this.pageSize=pageSize;
        this.totalNum=totalNum;
    }
    public HotelResult(int state, String msg) {
        this.msg = msg;
        this.state = state;
    }
    public HotelResult(T data, Integer pageIndex, Integer pageSize, Integer totalNum, int state, String msg) {
        this(data);
        this.pageIndex=pageIndex;
        this.pageSize=pageSize;
        this.totalNum=totalNum;
        this.msg = msg;
        this.state = state;
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

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("state=").append(state);
        sb.append(",message='").append(message).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append(", pageIndex=").append(pageIndex);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", totalNum=").append(totalNum);
        sb.append('}');
        return sb.toString();
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public int getPageCount() {
		if(pageSize!=0){	
			pageCount = totalNum/pageSize;
			if (totalNum % pageSize > 0) {
				pageCount++;
			}
		}
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}

