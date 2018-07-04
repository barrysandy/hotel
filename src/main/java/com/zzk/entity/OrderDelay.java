package com.zzk.entity;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class OrderDelay implements Delayed {  
    
    private long orderNo;  
    private long startTime;  
      
    public OrderDelay(){  
          
    }  
      
    /** 
     * orderNo:订单orderNo 
     * timeout：超时时间，秒 
     * */  
    public OrderDelay(long orderNo, int timeout){  
        this.orderNo = orderNo;  
        this.startTime = System.currentTimeMillis() + timeout*1000L;  
    } 
    public OrderDelay(long orderNo, int timeout,Date date){  
        this.orderNo = orderNo;  
        this.startTime = date.getTime() + timeout*1000L;  
    }
    @Override  
    public int compareTo(Delayed other) {  
        if (other == this){  
            return 0;  
        }  
        if(other instanceof OrderDelay){  
            OrderDelay otherRequest = (OrderDelay)other;  
            long otherStartTime = otherRequest.getStartTime();  
            return (int)(this.startTime - otherStartTime);  
        }  
        return 0;  
    }  
  
    @Override  
    public long getDelay(TimeUnit unit) {  
        return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);  
    }  
  
    @Override  
    public int hashCode() {  
        final int prime = 31;  
        int result = 1;  
        result = prime * result + (int) (orderNo ^ (orderNo >>> 32));  
        result = prime * result + (int) (startTime ^ (startTime >>> 32));  
        return result;  
    }  
  
    @Override  
    public boolean equals(Object obj) {  
        if (this == obj)  
            return true;  
        if (obj == null)  
            return false;  
        if (getClass() != obj.getClass())  
            return false;  
        OrderDelay other = (OrderDelay) obj;  
        if (orderNo != other.orderNo)  
            return false;  
        if (startTime != other.startTime)  
            return false;  
        return true;  
    }  
  
    public long getStartTime() {  
        return startTime;  
    }  
  
    public long getOrderNo() {  
        return orderNo;  
    }  
  
    public void setOrderId(long orderId) {  
        this.orderNo = orderNo;  
    }  
  
    public void setStartTime(long startTime) {  
        this.startTime = startTime;  
    }  
  
    @Override  
    public String toString() {  
        return "OrderDelay [orderNo=" + orderNo + ", startTime=" + startTime + "]";  
    }

	 
}  