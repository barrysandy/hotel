package com.zzk.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zzk.util.StringUtils;
import org.springframework.stereotype.Service;

import com.zzk.dao.LoginLogMapper;
import com.zzk.entity.LoginLog;
import com.zzk.service.LoginLogService;
import com.zzk.util.DateUtils;

/**
 * 登陆日志
 *
 * @author：huashuwen
 * @date：2018-03-08 10:19
 */
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {
    @Resource
    private LoginLogMapper loginLogMapper;

    /**
     * 分页查询
     */
    @Override
    public List<LoginLog> selectByPage(Map map) {
        List<LoginLog> list = loginLogMapper.selectByPage(map);
        for (LoginLog log : list) {
            log.setLoginDateStr(DateUtils.changeDateToStr(log.getLoginDate(), DateUtils.CHINESE_DATE_FORAMT_YMD));
            log.setLoginTimeStr(DateUtils.changeDateToStr(log.getLoginTime(), DateUtils.CHINAESE_DATETIME_FORMAT_YMDHMS));
            String sellerPhone = log.getSellerPhone();
            if (StringUtils.isNotBlank(sellerPhone) && sellerPhone.length() >= 10){
                String start = sellerPhone.substring(0,3);
                String end = sellerPhone.substring(sellerPhone.length()-4,sellerPhone.length());
                sellerPhone = start + "****" + end;
                log.setSellerPhone(sellerPhone);
            }

        }
        return list;
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return loginLogMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public LoginLog selectByPrimaryKey(String id) {
        return loginLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(LoginLog bean) {
        return loginLogMapper.updateByPrimaryKey(bean);
    }

    /**
     * 新增
     *
     * @param bean
     * @return
     * @author：huashuwen
     * @date：2018-03-08 10:19
     */
    @Override
    public int insert(LoginLog bean) {

        return 1;//loginLogMapper.insert(bean);
    }


    /**
     * 逻辑删除
     *
     * @return
     * @author：huashuwen
     * @date：2018-03-08 10:19
     */
    @Override
    public int delete(String id) {
        LoginLog bean = loginLogMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return loginLogMapper.updateByPrimaryKey(bean);
    }

}
