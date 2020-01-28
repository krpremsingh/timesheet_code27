package com.awcsoftware.app.mail;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
	static Logger logger = Logger.getLogger(MailController.class.getName());
	@Autowired
	MailService service;

	@GetMapping("/WelcomeEmail")
	public String sendWelcomeMail(MailPojo mailpojo){
		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		emailExecutor.execute(new Runnable() {
			@Override
			public void run() {

				try {
					service.sendWelcomeEmail(mailpojo);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		emailExecutor.shutdown();
		

		return MailMessageConstants.SendEmail.getLabel().toString();

	}

}
