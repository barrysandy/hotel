package com.zzk.service;

public interface EmailService {
	
	int sendEmail(String from, String to, String subject,String text);
	
}
