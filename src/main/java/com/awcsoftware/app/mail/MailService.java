package com.awcsoftware.app.mail;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.awcsoftware.spring.security.auth.user.User;

@Component
public class MailService {

	@Autowired
	MailDao maildao;

	@Autowired
	Mail mail;

	public List<User> sendWelcomeEmail(MailPojo mailpojo) throws MessagingException, IOException {
		List<User> result = maildao.getEmailList();
		if (result != null) {
			mailpojo.setUserList(result);
			mail.welcomeEmail(mailpojo);
			for (User user : result) {
				maildao.updatemailFlag(mailpojo);
			}
			return result;
		}
		return null;

	}

}
