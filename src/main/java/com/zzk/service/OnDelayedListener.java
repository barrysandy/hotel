package com.zzk.service;

import com.zzk.entity.OrderDelay;

public interface OnDelayedListener{
    public void onDelayedArrived(OrderDelay order);  
    
    public void onDelayedPrepareStockArrived(OrderDelay order);  
}  
