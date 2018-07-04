package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.IcoMapper;
import com.zzk.entity.Ico;
import com.zzk.service.IcoService;

/**
 * 
 * 图标
* @author：huashuwen
* @date：2017-11-28 11:53
 */
@Service("icoService")
public class IcoServiceImpl implements IcoService {
	@Resource
	private IcoMapper icoMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<Ico> selectByPage(Map map) {
		return icoMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return icoMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public Ico selectByPrimaryKey(String id) {
		return icoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(Ico bean) {
		return icoMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 
	* 新增
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	@Override
	public int insert(Ico bean) {
		return icoMapper.insert(bean);
	}
	
	/**
	 * 
	* 逻辑删除
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2017-11-28 11:53
	 */
	@Override
	public int delete(String id) {
		Ico bean = icoMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return icoMapper.updateByPrimaryKey(bean);
	}

	@Override
	public List<Ico> selectIco() {
		return icoMapper.selectIco();
	}

}
