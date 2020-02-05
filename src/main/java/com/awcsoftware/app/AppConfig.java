package com.awcsoftware.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.awcsoftware.app.client.ClientDao;
import com.awcsoftware.app.client.ClientService;
import com.awcsoftware.app.client.ClientValidator;
import com.awcsoftware.app.common.BasePojo;
import com.awcsoftware.app.employee.ConfirmationToken;
import com.awcsoftware.app.employee.EmployeeDao;
import com.awcsoftware.app.employee.EmployeeLoginTransaction;
import com.awcsoftware.app.employee.EmployeeService;
import com.awcsoftware.app.employee.EmployeeValidator;
import com.awcsoftware.app.mail.Mail;
import com.awcsoftware.app.mail.MailConfig;
import com.awcsoftware.app.report.ReportsService;
import com.awcsoftware.spring.security.auth.user.UserDao;

@ComponentScan(basePackages = "com.awcsoftware")
@Configuration
public class AppConfig {

	@Bean
	public EmployeeValidator employeeValidator() {
		return new EmployeeValidator();
	}
	
	@Bean
	public ClientDao clientDao() {
		 return new ClientDao();
	}
	
	@Bean
	public ClientService clientService() {
		return new ClientService();
	}
	@Bean
	public ClientValidator clientValidator() {
		return new ClientValidator();
	}
/*	
	@Bean
	public ReportsValidator reportsValidator() {
		return new ReportsValidator();
	}*/
	
	@Bean
	public ReportsService reportservice() {
		return new ReportsService();
	}

	@Bean
	public EmployeeLoginTransaction loginTransaction() {
		return new EmployeeLoginTransaction();
	}

	@Bean
	public MailConfig mailConfig() {
		return new MailConfig();
	}

	@Bean
	public Mail mail() {
		return new Mail();
	}

	@Bean
	public ConfirmationToken cToken() {
		return new ConfirmationToken();

	}

	@Bean
	public BasePojo basePojo() {
		return new BasePojo();
	}

	@Bean
	public EmployeeService employeeService() {
		return new EmployeeService();
	}

	@Bean
	public EmployeeDao employeeDao() {
		return new EmployeeDao();
	}

	@Bean
	public UserDao userDao() {
		return new UserDao();
	}

}
