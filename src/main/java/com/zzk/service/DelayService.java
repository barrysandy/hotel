package com.zzk.service;


import java.util.concurrent.DelayQueue;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzk.Constant;
import com.zzk.common.Constact;
import com.zzk.entity.OrderDelay;

@Service  
public class DelayService {  
      
      
    private boolean start ;    
    private OnDelayedListener listener;  
    private DelayQueue<OrderDelay> delayQueue = new DelayQueue<OrderDelay>();  
    private DelayQueue<OrderDelay> dealPrepareStockQueue = new DelayQueue<OrderDelay>();
    
    public void start(OnDelayedListener listener){  
        if(start){  
            return;  
        }  
        System.err.println("DelayService 启动");  
        start = true;  
        this.listener = listener;  
        new Thread(new Runnable(){  
            public void run(){  
                try{  
                    while(true){  
                    	//处理未支付订单改变状态为已取消
                    	System.err.println("未支付订单阻塞");
                        OrderDelay order = delayQueue.take();  
                        if(DelayService.this.listener != null){  
                            DelayService.this.listener.onDelayedArrived(order);  
                        }
                        System.err.println("未支付订单改变完成");
                        
                    }  
                }catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }).start(); 
        new Thread(new Runnable(){  
            public void run(){  
                try{  
                    while(true){  
                    	System.err.println("预占库存阻塞");
                    	//处理未支付订单的预占库存
                        OrderDelay prepareStockOrder = dealPrepareStockQueue.take(); 
                        System.err.println("OOOO"+prepareStockOrder.getOrderNo());
                        if(DelayService.this.listener != null){  
                            DelayService.this.listener.onDelayedPrepareStockArrived(prepareStockOrder);  
                        }
                        System.err.println("预占库存改变");
                    }  
                }catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }).start();
    }  
      
    public void add(OrderDelay order){  
        delayQueue.put(order);  
    }  
  
    public boolean remove(OrderDelay order){  
        return delayQueue.remove(order);  
    }
    
    public void addPrepare(OrderDelay order){  
    	dealPrepareStockQueue.put(order);  
    }  
  
    public boolean removePrepare(OrderDelay order){  
        return dealPrepareStockQueue.remove(order);  
    }
    
      
    public void add(long orderNo){  
        delayQueue.put(new OrderDelay(orderNo, Constant.EXPTIME));  
    }  
      
    public void remove(long orderNo){  
        OrderDelay[] array = delayQueue.toArray(new OrderDelay[]{});  
        if(array == null || array.length <= 0){  
            return;  
        }  
        OrderDelay target = null;  
        for(OrderDelay order : array){  
            if(order.getOrderNo() == orderNo){  
                target = order;  
                break;  
            }  
        }  
        if(target != null){  
            delayQueue.remove(target);  
        }  
    }
    
    public void addPrepare(long orderNo){  
    	dealPrepareStockQueue.put(new OrderDelay(orderNo, Constant.PREPARE_STOCK_TIME));  
    }  
      
    public boolean removePrepare(long orderNo){  
        OrderDelay[] array = dealPrepareStockQueue.toArray(new OrderDelay[]{});  
        if(array == null || array.length <= 0){  
            return false;  
        }  
        OrderDelay target = null;  
        for(OrderDelay order : array){  
            if(order.getOrderNo() == orderNo){  
                target = order;  
                break;  
            }  
        }  
        if(target != null){  
        	return dealPrepareStockQueue.remove(target);  
        }
        return false;
    }

	

 
} 