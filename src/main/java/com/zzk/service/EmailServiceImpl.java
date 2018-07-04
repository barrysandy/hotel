package com.zzk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zzk.util.EmailUtil;
import com.zzk.service.EmailService;



/**   
 * @Description:  
 * @author sty   
 * @date 2017年4月11日 下午2:07:47 
 * @version V1.0   
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService{

	@Value("${email.host}")  
	private String emailHost;
	
	 
	private String emailUserName = "zsxx@zhishengxixing.com";
	
	@Value("${email.password}")  
	private String emailPassword;
	
	@Value("${email.timeout}")  
	private String emailTimeout;
	
	/* (non-Javadoc)
	 * @see com.wisesoft.wisdom.trip.mall.service.EmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	
	@Override
	public int sendEmail(String from, String to, String subject,String text) {
		
		EmailUtil emailUtil = new EmailUtil();
		try {
			emailUtil.sendEmail(emailHost, from, to, subject, text, emailUserName, emailPassword, emailTimeout);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return 0;
	}
	
	

}
