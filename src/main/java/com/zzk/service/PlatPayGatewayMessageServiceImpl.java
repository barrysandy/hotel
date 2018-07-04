package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzk.dao.PlatPayGatewayMessageMapper;
import com.zzk.entity.PlatPayGatewayMessage;
import com.zzk.service.PlatPayGatewayMessageService;

/**
 * 
 * 网关报文
* @author：zhou.zhengkun
* @date：2017-03-24 17:16
 */
@Service("platPayGatewayMessageService")
@Transactional
public class PlatPayGatewayMessageServiceImpl implements PlatPayGatewayMessageService {
	@Resource
	private PlatPayGatewayMessageMapper platPayGatewayMessageMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<PlatPayGatewayMessage> selectByPage(Map map) {
		return platPayGatewayMessageMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return platPayGatewayMessageMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public PlatPayGatewayMessage selectByPrimaryKey(String id) {
		return platPayGatewayMessageMapper.selectByPrimaryKey(id);
	}
	/**
	 * 订单ID查询
	 */
	@Override
	public List<PlatPayGatewayMessage> selectByOrderId(String id) {
		return platPayGatewayMessageMapper.selectByOrderId(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(PlatPayGatewayMessage bean) {
		return platPayGatewayMessageMapper.updateByPrimaryKey(bean);
	}
	
	/**
	 * 部分更新
	 */
	@Override
	public int updateByPrimaryKeySelective(PlatPayGatewayMessage bean) {
		return platPayGatewayMessageMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：zhou.zhengkun
    * @date：2017-03-24 17:16
	 */
	@Override
	public int insert(PlatPayGatewayMessage bean) {
		return platPayGatewayMessageMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：zhou.zhengkun
    * @date：2017-03-24 17:16
	 */
	@Override
	public int delete(String id) {
		PlatPayGatewayMessage bean = platPayGatewayMessageMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return platPayGatewayMessageMapper.updateByPrimaryKey(bean);
	}

}
