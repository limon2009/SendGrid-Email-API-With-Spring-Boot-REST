package com.monirthought.sendgrid.service;

import com.monirthought.sendgrid.pojo.EmailPojo;

/**
 * SendGridService to send email using SendGrid API
 * 
 * @author Moniruzzaman Md
 *
 */
public interface SendGridService {

	/**
	 * Send email
	 * 
	 * @param emailPojo
	 */
	public String sendMail(EmailPojo emailPojo);
}
