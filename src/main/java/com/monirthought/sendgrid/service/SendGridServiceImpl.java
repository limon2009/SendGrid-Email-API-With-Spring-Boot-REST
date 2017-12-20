package com.monirthought.sendgrid.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.monirthought.sendgrid.pojo.EmailPojo;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

/**
 * Service class is to configure and send email using SendGrid API
 * 
 * @author Moniruzzaman Md
 *
 */
@Service
public class SendGridServiceImpl implements SendGridService {

	final private String sendGridApi = "Your_SendGripd_API_Key";

	/**
	 * PersonalizeEmail - details setting for each email. For the complete example:
	 * https://github.com/sendgrid/sendgrid-java/blob/master/examples/helpers/mail/Example.java
	 * 
	 * @param emailPojo
	 * @return Mail
	 */
	private Mail PersonalizeEmail(EmailPojo emailPojo) {

		Mail mail = new Mail();

		/* From information setting */
		Email fromEmail = new Email();
		fromEmail.setName(emailPojo.getFromName());
		fromEmail.setEmail(emailPojo.getFromEmail());

		mail.setFrom(fromEmail);
		mail.setSubject(emailPojo.getEmailSubject());

		/*
		 * Personalization setting, we only add recipient info for this particular
		 * example
		 */
		Personalization personalization = new Personalization();
		Email to = new Email();
		to.setName(emailPojo.getToName());
		to.setEmail(emailPojo.getToEmail());
		personalization.addTo(to);

		personalization.addHeader("X-Test", "test");
		personalization.addHeader("X-Mock", "true");

		/* Substitution value settings */
		personalization.addSubstitution("%name%", emailPojo.getToName());
		personalization.addSubstitution("%from%", emailPojo.getFromName());

		mail.addPersonalization(personalization);

		/* Set template id */
		mail.setTemplateId("Your_SendGrid_Template_Id");

		/* Reply to setting */
		Email replyTo = new Email();
		replyTo.setName(emailPojo.getFromName());
		replyTo.setEmail(emailPojo.getFromEmail());
		mail.setReplyTo(replyTo);

		/* Adding Content of the email */
		Content content = new Content();

		/* Adding email message/body */
		content.setType("text/plain");
		content.setValue(emailPojo.getMessage());
		mail.addContent(content);

		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.monirthougth.sendgrid.service.SendGridService#sendMail(com.monirthought.
	 * sendgrid.pojo.EmailPojo)
	 */
	@Override
	public String sendMail(EmailPojo emailPojo) {

		SendGrid sg = new SendGrid(sendGridApi);
		sg.addRequestHeader("X-Mock", "true");

		Request request = new Request();
		Mail mail = PersonalizeEmail(emailPojo);
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			ex.printStackTrace();
			return "Failed to send mail! " + ex.getMessage();
		}
		return "Email is sent Successfully!!";
	}

}
