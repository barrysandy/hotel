package com.zzk.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



//import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.zzk.util.Exceptions;
import com.zzk.util.Result;
import com.zzk.util.DateUtils;
import com.zzk.util.StringUtils;
import com.zzk.util.RedisUtils;
import com.zzk.entity.MessageConfigCustom;
import com.zzk.entity.MessageContent;
import com.zzk.link.IndustrySMS;
import com.zzk.link.Template;
import com.zzk.service.EmailService;
import com.zzk.service.MessageConfigService;
import com.zzk.service.MessageContentService;

@Component("messageService")
public class MessageService {
   /**
    * 酒店的ID
    */
	private String shopId;
	/**
	 * 发送的类型
	 */
	private int sendType;
	/**
	 * 新订单提醒标号
	 */
	
	private final String messageTemplateId = "228133";
	
	private final String platForm="达西酒店管理系统";
	
	private final String platFromWeb="http://www.daxi51.com/merchant/#/login";
	public static final int NEWORDER_SENDTYPE=1;
	/**
	 * 退款提醒标号
	 */
	public static final int REFUND_SENDTYPE=2;
	/**
	 * 取单提醒标号
	 */
	public static final int CANCELORDER_SENDTYPE=3;
	/**
	 * 差评提醒标号
	 */
	public static final int BADCOMMENT_SENDTYPE=4;
	/**
	 * 财务提醒标号
	 */
	public static final int FINANCIAL_SENDTYPE=5;
	/**
	 * 满房提醒标号
	 */
	public static final int FULLROOM_SENDTYPE=6;
		/**
		 * 新订单提醒内容
		 */
		public static final String NEWORDER_CONTENT="订单";
		/**
		 * 退款提醒内容
		 */
		public static final String REFUND_CONTENT="退款";
		/**
		 * 取单提醒内容
		 */
		public static final String CANCELORDER_CONTENT="取消订单";
		/**
		 * 差评提醒内容
		 */
		public static final String BADCOMMENT_CONTENT="差评";
		/**
		 * 财务提醒内容
		 */
		public static final String FINANCIAL_CONTENT="财务";
		/**
		 * 满房提醒内容
		 */
		public static final String FULLROOM_CONTENT="满房";
	    private MessageConfigCustom config;
	    @Autowired
	    private MessageConfigService messsageConfigService;
	    @Autowired
	    private MessageContentService messageContentService;
	    @Autowired
	    private EmailService emailService;
	    
	    @Autowired
	    private RedisUtils redisUtils;
	    private final String serverIp = "app.cloopen.com";
	    private final String serverPort = "8883";
	    private final String accountSid = "8a216da85da6adf7015de3fb1d7115f9";
	    private final String accountToken = "ede7077c59f740d088396517669fe445";
	    private final String appId = "8aaf07085dcad420015de3fe05150738";
	/**
	 * 发送消息
	 * @param userId 用户ID
	 * @param SENDTYPE 发送类型 :1、新订单提醒 2、退款提醒 3、取单提醒 4、差评提醒 5、财务提醒 6、满房提醒
	 * @return Map
	 */
	public Result sendMessage(String shopId,int sendType){
		this.shopId = shopId;
		this.sendType = sendType;
		config = messsageConfigService.selectByShopId(shopId);
		try{
			if(config != null){
				if(sendType==NEWORDER_SENDTYPE){
					String[] modes = config.getNewOrderMsgs();
					return parseMode(modes,NEWORDER_CONTENT);
				}else if(sendType==REFUND_SENDTYPE){
					String[] modes = config.getRefundMsgs();
					return parseMode(modes,REFUND_CONTENT);
				}else if(sendType==CANCELORDER_SENDTYPE){
					String[] modes = config.getCancelOrderMsgs();
					return parseMode(modes,CANCELORDER_CONTENT);
				}else if(sendType==BADCOMMENT_SENDTYPE){
					String[] modes = config.getBadCommentMsgs();
					return parseMode(modes,BADCOMMENT_CONTENT);
				}else if(sendType==FINANCIAL_SENDTYPE){
					String[] modes = config.getFinancialMsgs();
					return parseMode(modes,FINANCIAL_CONTENT);
				}else if(sendType== FULLROOM_SENDTYPE){
					String[] modes = config.getFullRoomMsgs();
					return parseMode(modes,FULLROOM_CONTENT);
				}
			}
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);
		}
		return null;
	}
	
	private  Result parseMode(String[] modes,String type){
		Map<String,String> map = new HashMap<String,String>();
		for (int i = 0; i < modes.length; i++) {
			String mode = modes[i];
			if("1".equals(mode)){
			   MessageContent content = new MessageContent();
			   content.setId(UUID.randomUUID().toString());
			   content.setCreateTime(DateUtils.getDate());
			   content.setShopId(shopId);
			   content.setMsgType(sendType);
			   content.setContent(type);
			   content.setReadStatus(1);
			   content.setStatus(1);
			   messageContentService.insertSelective(content);
			}
			if("2".equals(mode)){
				String phone = config.getMessagePhone();
				if(StringUtils.isNotBlank(phone)){
					HashMap<String, Object> link = IndustrySMS.link(phone, Template.MESSAGE_CODE, "", modes);
				}
			}
			if("3".equals(mode)){
				String email = config.getMessageEmail();
				if(StringUtils.isNotBlank(email)){
					sendEmail(config.getMessageEmail(),type,type);
				}
			}
			if("4".equals(mode)){
			//电话通知	
			}
			
		}
		
		return null;
	}
	
	private Result<Object> sendEmail(String to,String subject,String text){
		Result<Object> result = new Result<Object>();
        if (StringUtils.isEmpty(to)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("email不能为空");
            return result;
        }
        subject = "【达西云商】一个新的"+subject+"需要您及时处理";
        text=platForm+platFromWeb;
        int code = emailService.sendEmail(null, to, subject, text);
        if(code==1) {
            result.setState(1);
            result.setMsg("success");
            result.setMessage("验证邮箱信息已发送");
        }else{
            result.setState(0);
            result.setMsg("error");
            result.setMessage("邮箱服务器异常");
        }
        return result;
		}
/*	private Result sendCode(String phoneNum, String templateId,String temple) {
        Result result = new Result();
        try {
            HashMap<String, Object> resultMap = null;
            CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
            // *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
            // *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
            // *******************************************************************************
            //短信接口调用准备:1 初始化,2 设置账户,3 设置应用ID
            restAPI.init(serverIp, serverPort);
            restAPI.setAccount(accountSid, accountToken);
            restAPI.setAppId(appId);
            
//            //使用随机数生成一个6位数验证码
//            String yzmStr = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//            
//            redisUtils.setDataByKey(phoneNum, yzmStr, 600);
            // 参数说明:1:电话号码  2短信模版ID(免费测试模版为1) 3第一个为短信内容,第二个是几分钟之内输入
            resultMap = restAPI.sendTemplateSMS(phoneNum, templateId, new String[]{"1",temple, platForm,platFromWeb});
            String statusCode = (String) resultMap.get("statusCode");
            if (StringUtils.equals("000000", statusCode)) {
                result.setState(1);
                result.setMsg("success");
                result.setMessage("验证短信已下发");
            } else if (StringUtils.equals("160040", statusCode)){
                result.setState(0);
                result.setMsg("error");
                result.setMessage("同一手机一天发送验证码次数超过限制");
            } else if (StringUtils.equals("160038", statusCode)) {
                result.setState(0);
                result.setMsg("error");
                result.setMessage("短信验证码发送过频繁");
            } else {
                result.setState(0);
                result.setMsg("error");
                result.setMessage("验证短信发送不成功"+resultMap.get("statusMsg"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }*/
}
