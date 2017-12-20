package com.monirthought.sendgrid.controller;

import org.springframework.web.bind.annotation.RestController;

import com.monirthought.sendgrid.pojo.EmailPojo;
import com.monirthought.sendgrid.service.SendGridService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Simple Controller class to handle HTTP request.
 * 
 * @author Moniruzzaman
 *
 */
@RestController
public class IndexController {

	@Autowired
	SendGridService sendGridService;

	@RequestMapping(value = "/email/", method = RequestMethod.POST)
	public String index(@RequestBody EmailPojo emailPojo) {
		String response = sendGridService.sendMail(emailPojo);
		return response;
	}

}
